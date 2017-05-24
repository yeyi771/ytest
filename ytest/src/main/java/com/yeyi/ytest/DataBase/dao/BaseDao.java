package com.yeyi.ytest.DataBase.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.yeyi.ytest.DataBase.util.SqlContext;
import com.yeyi.ytest.DataBase.util.SqlUtils;

public class BaseDao<T> {

	@Autowired
	@Qualifier("namedParameterJdbcTemplate") //自测不用写这个也是可以的
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @param entity
	 * @return the number of rows affected,
	 */
	public synchronized int insert(T entity, String tableName) {
	    final SqlContext sqlContext = SqlUtils.buildInsertSql(entity, tableName);  
		return jdbcTemplate.update(sqlContext.getSqlStr(), sqlContext.getParams());
	}
	
	public synchronized int update(T entity, String tableName, String primaryName){
		final SqlContext sqlContext = SqlUtils.buildUpdateSql(entity, tableName, primaryName);  
		return jdbcTemplate.update(sqlContext.getSqlStr(), sqlContext.getParams());
	}
}
