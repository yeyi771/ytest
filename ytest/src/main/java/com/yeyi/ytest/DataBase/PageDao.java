package com.yeyi.ytest.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import org.apache.hadoop.mapred.jobdetailshistory_jsp;
//import org.apache.poi.ss.formula.functions.Offset;
//import org.aspectj.weaver.IWeavingSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

//import com.iflashbuy.base.util.CollectionUtil;
//import com.iflashbuy.service.Freshnews.dto.FreshnewsDTO;


/**
 * 从数据库中某表取分页数据通用类
 * @author yeyi
 * 
 */
public class PageDao <T> {
	private NamedParameterJdbcTemplate jdbcTemple = null;
	private ArrayList<String> attribleList = new ArrayList<String>();	// 要从表中取出的（哪些）字段 的List
	private Map<String, Object> whereMap=new HashMap<String,Object>();	// 用于拼凑 where 后面的条件对
	private String              tableName;
	private int                 page = 1;
	private int                 pageSize = 10;
	private int                 pageMax = 0;
	
	public void SetJdbcTemplate(NamedParameterJdbcTemplate t){
		this.jdbcTemple = t;
	};
	
	NamedParameterJdbcTemplate GetJdbcTemplate(){
		return this.jdbcTemple;
	}
	
	// 增加分页时要取的字段,如果查询时一个字段都没有（0==attribleList.size()）表示取数据表的所有字段
	public void AddAttrible(String a){
		attribleList.add(a);
	};
	
	// 增加一个查询的 where 条件对，目前只支持 where key=:value 这样的 where 语句
	// 没有 where 语句(0==whereMap.size())则查询整个表所有数据
	public void AddWhere(String key, Object value){
		whereMap.put(key, value);
	};
	
	public void ClearWhere(){
		whereMap.clear();
	};
	
	public void SetTableName(String name){
		this.tableName = name;
	}
	
	public void SetPage(int p){
		this.page = p;
	};
	
	public void SetPageSize(int ps){
		this.pageSize = ps;
	}
	
	// 组装 where 语句
	protected String WhereSqlString(){
		if( 0==whereMap.size() ) return "";
		// else
		
		String sql = "WHERE ";
		Iterator<Map.Entry<String,Object>> entries = whereMap.entrySet().iterator();
		Map.Entry<String,Object> entry2 = (Map.Entry<String,Object>) entries.next();
		sql += (entry2.getKey() + "=" + " :" + entry2.getKey());	// 注意这里前后都用的 key
	    
		while(entries.hasNext()){
			Map.Entry<String,Object> entry = (Map.Entry<String,Object>) entries.next();
			sql += (" AND " + entry.getKey() + "=" + " :" + entry.getKey()); 
		}
		
		return sql;
	}
	
	// 生成查询  sql 语句
	// isGetTotal: true是查询条数的语句，false为查询分页数据的语句
	protected String MakeSqlStringQuery(boolean isGetTotal){
		if( tableName==null || tableName=="" ) return null;
		
		String sql = "SELECT ";
		if( isGetTotal ){
			sql += "COUNT(*)";
		}
		else{
			// 为空则取所有字段
			if( 0==attribleList.size() ) sql += "*";
			else{
				sql += attribleList.get(0);
				for(int i=1; i<attribleList.size(); i++)
					sql += ("," + attribleList.get(i));	
			}
		}
		
		sql += " FROM " + tableName;
		String where = WhereSqlString();
		if( where!="" ) sql += (" " + where);
		
		return sql;
	}
	
	// 得到符合 where 查询的条数
	// 出错返回 -1
	protected int GetTotal(){
		if( jdbcTemple==null ) return -1;
		if( this.pageSize<=0 ) return -1;
		
		String sqlCount = MakeSqlStringQuery(true);
		if( null==sqlCount || sqlCount=="" ) return -1;
		
		int total = jdbcTemple.query(sqlCount, whereMap, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return -1;
			}
		});
		
		return total;
	}
	
	// 出错返回 -1
	protected int GountPageMax(){
		int total = GetTotal();
		if( total<0 ) return -1;
		if( 0==total ) this.pageMax = 0;
		else if( 0!=(total%pageSize) ) this.pageMax = total/pageSize + 1;
		else this.pageMax = total/pageSize;
		
		return this.pageMax;
	}
	
	// 出错返回 null
	public List<T> getPageList(Class<T> clazz) {
		int count = GountPageMax();
		if( count<=0 ) return null;
		
		String querySql = MakeSqlStringQuery(false);
		
		// 加上分页语句
		querySql += (" LIMIT :offset, :pageSize");
		
		// 构造增加了分页变量的 paramMap
		Map<String, Object> paramMap=new HashMap<String,Object>();
		//tmpMap = whereMap;	//eee 注意不能这样复制，否则修改 tmpMap 即是修改 whereMap
		paramMap.putAll(whereMap);
		paramMap.put("offset", this.page*this.pageSize );
		paramMap.put("pageSize", this.pageSize );
		
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		return jdbcTemple.query(querySql, paramMap, rowMapper);
	}
}
