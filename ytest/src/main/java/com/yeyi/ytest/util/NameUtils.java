package com.yeyi.ytest.util;

public class NameUtils {
	/**
     * 下划线转驼峰法
     * https://www.experts-exchange.com/questions/24573163/convert-underscore-to-camelcase.html
     * @param s 源字符串
     * @return 转换后的字符串
     */
    public static String underline2Camel(String s){
    	if( null==s || "".equals(s) )
    		return s;
    	String[] atoms = s.split("_");
        StringBuilder camel = new StringBuilder();
        for (String atom : atoms) {
            if (atom.length() > 0) {
                camel.append(Character.toUpperCase(atom.charAt(0)));
            }
            if (atom.length() > 1) {
                camel.append(atom.substring(1).toLowerCase());
            }
        }
        return camel.toString();
    }
    
    /**
     * 驼峰转下划线
     * https://www.experts-exchange.com/questions/24573163/convert-underscore-to-camelcase.html
     * @param s 源字符串
     * @return 转换后的字符串
     */
    public static String Camel2UnderlineRegex(String s){
    	if( null==s || "".equals(s) )
    		return s;
    	
    	String regex = "([A-Z][a-z]+)";
    	String replacement = "$1_";
//    	String regex = "([a-z])([A-Z]+)";
//        String replacement = "$1_$2";

    	String tmp = s.replaceAll(regex, replacement);
    	
    	return tmp;
    }
    
    public static void main(String[] args) {
		String s1 = "Camel2UnderlineRegex";
		System.out.println(Camel2UnderlineRegex(s1));
		
		s1 = "t_product_base";
		System.out.println(underline2Camel(s1));
	}
}
