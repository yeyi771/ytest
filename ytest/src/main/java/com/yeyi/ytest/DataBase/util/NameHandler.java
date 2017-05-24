package com.yeyi.ytest.DataBase.util;

public class NameHandler {
	private static final String UNDERLINE = "_";
	// 类字段(驼峰风格)转成下划线数据库字段（下划线风格）
	public static String entityName2ColumnName(String fieldName){
		StringBuffer builder = new StringBuffer();
		char[] charArray = fieldName.toCharArray();
		for(int index = 0; index < charArray.length; index++) {
			char ch = charArray[index];
			if(Character.isUpperCase(ch)) {
				if(index == 0) {
					builder.append(Character.toLowerCase(ch));
				} else {
					builder.append(UNDERLINE);
					builder.append(Character.toLowerCase(ch));
				}
			} else {
				builder.append(ch);
			}
		}
		return builder.toString();
	}
}
