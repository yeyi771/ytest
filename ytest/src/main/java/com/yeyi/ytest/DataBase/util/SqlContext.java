package com.yeyi.ytest.DataBase.util;

import java.util.Map;

/** 
 * 执行sql的上下文内容 
 *  
 * User: liyd 
 * Date: 2/13/14 
 * Time: 10:40 AM 
 */  
public class SqlContext {  
  
    /** 执行的sql */  
    private StringBuilder sql;  
  
    /** 主键名称 */  
//    private String        primaryKey;  
  
    /** 参数，对应sql中的?号 */  
    private Map<String, Object>  params;  
  
    public SqlContext(StringBuilder sql, Map<String, Object> params) {  
        this.sql = sql;  
//        this.primaryKey = primaryKey;  
        this.params = params;  
    }

	public StringBuilder getSql() {
		return sql;
	}
	
	public String getSqlStr() {
		return sql.toString();
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}

//	public String getPrimaryKey() {
//		return primaryKey;
//	}
//
//	public void setPrimaryKey(String primaryKey) {
//		this.primaryKey = primaryKey;
//	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}  