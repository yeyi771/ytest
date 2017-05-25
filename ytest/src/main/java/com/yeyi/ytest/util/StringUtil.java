package com.yeyi.ytest.util;

public class StringUtil {
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if("null".equalsIgnoreCase(str)) {
			return true;
		} else {
			str = str.trim();
			if (str.equals("")) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String upperFirstChar(String str) {
		if(str!=null && str!=""){
			str  = str.substring(0,1).toUpperCase()+str.substring(1);
		}
		return str;
	}
	
	public static String upperFirstChar2(String src) { // 没有空判断
		return src.substring(0, 1).toUpperCase().concat(src.substring(1));
	}
	
	// product_id -> productId
	public static String dbFieldToBeanField(String dbField){
		int index = dbField.indexOf("_");
		if( -1==index )
			return dbField;
		
		StringBuilder result = new StringBuilder();
		result.append(dbField.substring(0,index));
		String after = dbField.substring(index+1);
		after = upperFirstChar(after);
		after = dbFieldToBeanField(after); // 如果有多个下划线则递归
		result.append(after);
		
		return result.toString();
	}
	
	private static final String UNDERLINE = "_";
	// 类字段(驼峰风格)转成下划线数据库字段（下划线风格）
	public static String camelcase2Underscore(String fieldName){
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
	
	public static void main(String[] args) {
		String string = "camelcase2Underscore";
		System.out.println(camelcase2Underscore(string));
	}
}
