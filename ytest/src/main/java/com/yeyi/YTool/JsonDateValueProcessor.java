package com.yeyi.YTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.JsonConfig;

public class JsonDateValueProcessor implements JsonValueProcessor {
private String datePattern = "yyyy-MM-dd HH:mm:ss";// 日期格式
public JsonDateValueProcessor() {
super();
}
// 构造函数
public JsonDateValueProcessor(String format) {
super();
this.datePattern = format;
}
public Object processArrayValue(Object value, JsonConfig jsonConfig) {
// TODO Auto-generated method stub
return process(value);
}
public Object processObjectValue(String key, Object value,
JsonConfig jsonConfig) {
// TODO Auto-generated method stub
return process(value);
}
private Object process(Object value) {
try {
if (value instanceof Date) {
SimpleDateFormat sdf = new SimpleDateFormat(datePattern,
Locale.UK);
return sdf.format((Date) value);
}
return value == null ? "" : value.toString();
} catch (Exception e) {
return "";
}
}
public String getDatePattern() {
return datePattern;
}
public void setDatePattern(String datePaterns) {
this.datePattern = datePaterns;
}
public static void main(String[] args) {
//ArrayList list = new ArrayList();
//Users user = new Users();
//user.setDate(new Date());
//list.add(user);
//JsonConfig jsonConfig = new JsonConfig();
//// 设置javabean中日期转换时的格式
//jsonConfig.registerJsonValueProcessor(Date.class,
//new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
//// 获取json数组
//JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
//System.out.println(jsonArray.toString());
}
}
