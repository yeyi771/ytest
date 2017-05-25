package com.yeyi.ytest.common.aop;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yeyi.ytest.util.StringUtil;

public class InterceptorUtil {
	
	public static final String TOKEN_NAME = "token";
	public static final String PAGE_SIZE = "pageSize";
	public static final String IS_MICRO_BROWSER = "isMicroBrowser";
	
	// 对应 token.properties 中的key
	protected static final String IGNORE_URI_KEY = "ignoreTokenURI";
	protected static final String IGNORE_CLASS_KEY = "ignoreTokenClass";
	
	// token 过滤的白名单，存在的都不用
	private static Map<String, String> whiteMap = new HashMap<String, String>();
	private static Set<String> ignoreUriSet = null;
	private static Set<String> ignoreClassSet = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorUtil.class);
	
	static {
		init();
	}
	
	public static Set<String> asHashSet(String ids) {
		Set<String> setList = new HashSet<String>();
		if (StringUtil.isNotEmpty(ids)) {
			String idGroup[] = ids.split(",");
			for (String id : idGroup) {
				if(StringUtil.isNotEmpty(id)) {
					setList.add(id);
				}
			}
		}
		return setList;
	}
	
	protected static void init() {
		ResourceBundle rb = ResourceBundle.getBundle("interceptor");
		for (Iterator<String> iterator = rb.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			whiteMap.put(key, rb.getString(key));
		}
		
		ignoreUriSet = asHashSet(whiteMap.get(IGNORE_URI_KEY));
		if( ignoreUriSet.isEmpty() )
			LOGGER.warn(IGNORE_URI_KEY +" 's empty!");
		
		ignoreClassSet = asHashSet(whiteMap.get(IGNORE_CLASS_KEY));
		if( ignoreClassSet.isEmpty() )
			LOGGER.warn(IGNORE_URI_KEY +" 's empty!");
	}
	
	public static Set<String> getIgnoreUriSet() {
		return Collections.unmodifiableSet(ignoreUriSet);
	}
	
	public static Set<String> getIgnoreClissSet() {
		return Collections.unmodifiableSet(ignoreClassSet);
	}
}
