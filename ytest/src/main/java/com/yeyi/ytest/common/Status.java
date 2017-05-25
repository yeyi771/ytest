package com.yeyi.ytest.common;

public enum Status {
	
	SUCCESS("0","操作成功"),
	FAIL("1", "操作失败"),//
	SYS_ERROR("2","系统错误"),// 抛异常的用
	PARAMETER_ERROR("3","参数不合法"),
	
	EMPTY("4", "暂无数据"),
	
	SYS_IMAGE_UPLOAD_FAILED_ERROR("100001","图片上传失败"),
	SYS_TOKEN_INVALID_ERROR("100002", "Token无效"),
	
	SYS_PASSWOLD_ERROR("200001","密码错误"),
	SYS_ACCOUNT_ERROR("200002","账户错误"),
	
	PAY_ERROR("300001","调用支付接口失败"),
	PAY_REFUND_ERROR("300002","调用退款接口失败");
	
	
	private Status(String code,String message){
		this.code = code;
		this.message = message;
	}
	private String code;
	private String message;
	
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	

}
