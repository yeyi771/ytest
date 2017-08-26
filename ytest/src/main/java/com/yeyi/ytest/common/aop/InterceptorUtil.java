package com.yeyi.ytest.common.aop;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterceptorUtil {
    
    private static Logger logger = LoggerFactory.getLogger(InterceptorUtil.class);
    
    public static void setLogger(Logger log) {
        logger = log;
    }

    public static void printLog(HttpServletRequest request, Object handler){
        @SuppressWarnings("unchecked")
        Map<String, String[]> paramMap = request.getParameterMap();
        int i = 1;
        Iterator<String> iter = paramMap.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = paramMap.get(key);
            if( "pwd".equals(key) ){    // 隐藏密码
                value = "notPwd";
            }
            if(!"para".equals(key)) {
                logger.info("param "+i+": {}-{}", key, value);
                i = i + 1;
            }
        }
        
        String className = "";
        if( null!=handler )
            className = handler.getClass().getName();
        
        try {
            logger.info("Request Begin, URI:\r\n"+getRequstAddress(request)
            +"\r\n\r\nController: "+ className +", Request Type: "+request.getMethod()+", Protocol: "+request.getScheme());
        } catch (Exception e) {
            logger.info("printLog err: "+e.toString());
            e.printStackTrace();
        }
    }
    
    private static String getRequstAddress(HttpServletRequest request){
        String result ="";
        String reqMethod = request.getMethod();
        if("POST".equalsIgnoreCase(reqMethod)){
        	result = request.getRequestURL().toString();
        	
        	String allPara = getAllPostParam(request);
        	if( StringUtils.isNotEmpty(allPara) )
        		result += ("?" + allPara);
        }
        if("GET".equalsIgnoreCase(reqMethod)){
            result = request.getQueryString() != null ? 
                        request.getRequestURL().append("?").append(request.getQueryString()).toString() : 
                        request.getRequestURL().toString();
        }
        return result;
    }
    
    private static String getBody(HttpServletRequest request){
    	return "";
//    	String body = "";
//    	if( !StringUtils.containsIgnoreCase(request.getContentType(),"json") )
//    		return "";
//    	try {
//			body = IOUtils.toString(request.getReader()); // getReader 只能读一次,这里读了 Controller 中就无法读取了,要重载 HttpServletRequestWrapper 来解决
//		} catch (IOException e) {
//			logger.info("getBody err: "+e.toString());
//			e.printStackTrace();
//		}
//    	return body;
    }
    
    private static String getAllPostParam(HttpServletRequest request){
        @SuppressWarnings("unchecked")
        Map<String, String[]> postParam = request.getParameterMap();
        String queryString = "";
        for (String key : postParam.keySet()) {
            if( "pwd".equals(key) ){ // 隐藏密码
                queryString += key + "=" + "notPwd" + "&";
            }
            else{
                String[] values = postParam.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    queryString += key + "=" + value + "&";
                }
            }
        }
        if(StringUtils.isNotEmpty(queryString)){
            queryString = queryString.substring(0, queryString.length() - 1);
        }
        return queryString;
    }
}
