package com.yeyi.ytest.DataBase.util;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * sql辅助为类 
 *  
 * User: liyd 
 * Date: 2/13/14 
 * Time: 10:03 AM 
 */  
public class SqlUtils{  
  
    /** 日志对象 */  
    private static final Logger LOG = LoggerFactory.getLogger(SqlUtils.class);  
  
    /** 
     * 构建insert语句 
     * 
     * @param entity 实体映射对象 
     * @param nameHandler 名称转换处理器 
     * @return 
     */  
    public static SqlContext buildInsertSql(Object entity, String tableName) {  
        Class<?> clazz = entity.getClass();  
//        String primaryName = nameHandler.getPrimaryName(clazz.getSimpleName());  
        StringBuilder sql = new StringBuilder("insert into ");  
        Map<String, Object> params = new HashMap<String, Object>();
        sql.append(tableName);  
  
        //获取属性信息  
        BeanInfo beanInfo = ClassUtils.getSelfBeanInfo(clazz);  
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();  
        sql.append("(");  
        StringBuilder args = new StringBuilder();  
        args.append("(");  
        for (PropertyDescriptor pd : pds) {  
            Object value = getReadMethodValue(pd.getReadMethod(), entity);  
            if (value == null) {  
                continue;  
            }
            String colName = NameHandler.entityName2ColumnName(pd.getName()); 
            sql.append(colName);  
            args.append(":"+colName);
            params.put(colName, value);
            sql.append(",");  
            args.append(",");  
        }  
        sql.deleteCharAt(sql.length() - 1);  
        args.deleteCharAt(args.length() - 1);  
        args.append(")");  
        sql.append(")");  
        sql.append(" values ");  
        sql.append(args);  
        return new SqlContext(sql, params);  
    }  
  
    /** 
     * 构建更新sql 
     *  
     * @param entity 
     * @param nameHandler 
     * @return 
     */  
    public static SqlContext buildUpdateSql(Object entity, String tableName, String primaryName) {  
        Class<?> clazz = entity.getClass();  
        StringBuilder sql = new StringBuilder();  
        Map<String, Object> params = new HashMap<String, Object>();
        //获取属性信息  
        BeanInfo beanInfo = ClassUtils.getSelfBeanInfo(clazz);  
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();  
  
        sql.append("update ");  
        sql.append(tableName);  
        sql.append(" set ");  
        Object primaryValue = null;  
        for (PropertyDescriptor pd : pds) {  
            Object value = getReadMethodValue(pd.getReadMethod(), entity);  
            if (value == null) {  
                continue;  
            }  
            String colName = NameHandler.entityName2ColumnName(pd.getName());  
            if (primaryName.equalsIgnoreCase(colName)) {  
                primaryValue = value;  
            }  
            sql.append(colName);  
            sql.append(" = ");  
            sql.append(":"+colName);
            params.put(colName, value);
            sql.append(",");  
        }  
        sql.deleteCharAt(sql.length() - 1);  
        sql.append(" where ");  
        sql.append(primaryName);  
        sql.append(" = ");
        sql.append(":");
        sql.append(primaryName);
        params.put(primaryName, primaryValue);
        return new SqlContext(sql, params);  
    }  
  
    /** 
     * 构建查询条件 
     *  
     * @param entity 
     * @param nameHandler 
     */  
    public static SqlContext buildQueryCondition(Object entity, NameHandler nameHandler) {  
        //获取属性信息  
        BeanInfo beanInfo = ClassUtils.getSelfBeanInfo(entity.getClass());  
        //        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(entityClass);  
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();  
        StringBuilder condition = new StringBuilder();  
        Map<String, Object> params = new HashMap<String, Object>();
        int count = 0;  
        for (PropertyDescriptor pd : pds) {  
            Object value = getReadMethodValue(pd.getReadMethod(), entity);  
            if (value == null) {  
                continue;  
            }  
            if (count > 0) {  
                condition.append(" and ");  
            }
            String colName = NameHandler.entityName2ColumnName(pd.getName());
            condition.append(colName);  
            condition.append(":");
            condition.append(colName);
            params.put(colName, value);
            count++;  
        }  
        return new SqlContext(condition, params);  
    }  
  
    /** 
     * 获取属性值 
     * 
     * @param readMethod 
     * @param entity 
     * @return 
     */  
    private static Object getReadMethodValue(Method readMethod, Object entity) {  
        if (readMethod == null) {  
            return null;  
        }  
        try {  
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {  
                readMethod.setAccessible(true);  
            }  
            return readMethod.invoke(entity);  
        } catch (Exception e) {  
            LOG.error("获取属性值失败", e);  
            throw new RuntimeException(e);  
        }  
    }
}  