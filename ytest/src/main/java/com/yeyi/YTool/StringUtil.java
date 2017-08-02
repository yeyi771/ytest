package com.yeyi.YTool;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	
	public static boolean isNotEmpty(String str){
	    return !isEmpty(str);
	}
	
	 /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
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
	
	/**
     * 用逗号分隔的字符串转成数组
     */
	public static List<String> asList(String ids) {
        return asList(ids,",");
    }
    
    /**
     * 拆分字符串
     * @param ids 以同一个字符分隔开的字符串
     * @param sign 分隔字符
     * @return
     */
    public static List<String> asList(String ids, String sign) {
        List<String> idList = new LinkedList<String>();
        if (StringUtil.isNotEmpty(ids)) {
            String idGroup[] = ids.split(sign);
            for (String id : idGroup) {
                if(StringUtil.isNotEmpty(id)) {
                    idList.add(id.trim());
                }
            }
        }
        return idList;
    }
    
    /**
     * 用逗号分隔的字符串转成数字数组
     */
    public static List<Integer> asIntegerList(String ids) {
        List<Integer> idList = new LinkedList<Integer>();
        if (StringUtil.isNotEmpty(ids)) {
            String idGroup[] = ids.split(",");
            for (String id : idGroup) {
                if(StringUtil.isNotEmpty(id)) {
                    idList.add(Integer.parseInt(id.trim()));
                }
            }
        }
        return idList;
    }
    
    /**
     * 过滤掉转义字符(\r\n\t)
     * @return
     */
    public static String escapeCharacterFilter(String str){
        return str.replaceAll("\\s*", "");
    }
    
    /**
     * 随机生成 num 位数字
     * @param num
     * @return
     */
    public static String getRandomNumber(int num){
        if( num<=0 ) num = 4;
        String randomStr = new String("");
        int n[] = new int[num];
        for (int i=0;i<n.length;i++){
            n[i] = Math.abs(new Random().nextInt(10));
            
            randomStr = randomStr + String.valueOf(n[i]);
        }
        
        return randomStr;
    }
    
    /**
     * https://zhidao.baidu.com/question/2142533412771114668.html
     * 判断字符串 str 是否属于某种编码(encoding)
     * @param str
     * @param encoding "ISO-8859-1" "utf-8"
     * @throws UnsupportedEncodingException 
     */
    public static boolean checkEncoding(String str, String encoding) throws UnsupportedEncodingException{
        if( null==str )
            return true;
        
        return str.equals(new String(str.getBytes(encoding), encoding));
    }
    
    /** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
    public static final String US_ASCII = "US-ASCII";

    /** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /** 8 位 UCS 转换格式 */
    public static final String UTF_8 = "UTF-8";

    /** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
    public static final String UTF_16BE = "UTF-16BE";

    /** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
    public static final String UTF_16LE = "UTF-16LE";

    /** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
    public static final String UTF_16 = "UTF-16";

    /** 中文超大字符集 */
    public static final String GBK = "GBK";
    
    public static void main(String[] args) {
//        String string = "camelcase2Underscore";
//        System.out.println(camelcase2Underscore(string));
        
//        String ss = "123,wq,哈哈";
//        List<String> sList = asList(ss);
//        System.out.println(sList);
//        ss = "12333,0,3";
//        List<Integer> iList = asIntegerList(ss);
//        System.out.println(iList);
        
//        String string = "1322\r\nppp\t111";
//        System.out.println(escapeCharacterFilter(string));
        
        String ss = "123,wq,哈哈";
//        String encoding = US_ASCII;
        String encoding = UTF_8;
        try {
            System.out.println(checkEncoding(ss, encoding));
            System.out.println(checkEncoding("123", encoding));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
