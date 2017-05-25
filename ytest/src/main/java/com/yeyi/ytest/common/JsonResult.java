package com.yeyi.ytest.common;

import java.util.LinkedHashMap;

import org.springframework.util.Assert;

/**
 * Json结果返回对象
 * @author xhq
 *
 */
@SuppressWarnings("serial")
public class JsonResult extends LinkedHashMap<String,Object>{
	
	public final String CODE = "code";
	public final String MESSAGE = "message";
	public final String DATA = "data";
	
	public JsonResult(){
		super.put(CODE,Status.SUCCESS.getCode());
		super.put(MESSAGE,Status.SUCCESS.getMessage());
	}
	
	public JsonResult(Status status){
		super.put(CODE,status.getCode());
		super.put(MESSAGE,status.getMessage());
	}
	
	public void setStatus(Status status){
		super.put(CODE,status.getCode());
		super.put(MESSAGE,status.getMessage());
	}
	
	public JsonResult put(String key,Object value){
		Assert.notNull(key, "key值不能为null");
		super.put(key, value);
		return this;
	}

	/**
	 * app的格式返回：code/message/data
	 * new HashMap<string,object> 
	 * @param value
	 * @return
	 */
	public JsonResult setData(Object value){
		Assert.notNull(value, "value值不能为null");
		super.put(DATA, value);
		return this;
	}
	
	/**
	 * 场景：jsonResult.setStatus(Status.SYS_ERROR); <br>
	 *       使用status，又想修改值message的值。
	 * @param status
	 * @param message
	 */
	public void setStatus(Status status, String message){
		super.put(CODE,status.getCode());
		super.put(MESSAGE,message);
	}
}
