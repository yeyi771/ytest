package com.yeyi.YTool;

public class MethodName {
	private String getMethodName() {  
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();  
        // stacktrace[0].getMethodName() 是 getStackTrace
        // stacktrace[1].getMethodName() 是 getMethodName
        // stacktrace[2].getMethodName() 才是调用 getMethodName 的函数的函数名。
        StackTraceElement e = stacktrace[2];  
        String methodName = e.getMethodName();  
        return methodName;  
    }  
      
    public void getXXX() {  
        String methodName = getMethodName();  
        System.out.println(methodName);  
    }  
      
    public void getYYY() {  
        String methodName = getMethodName();  
        System.out.println(methodName);  
    }  
      
    public static void main(String[] args) {  
    	MethodName test = new MethodName();  
        test.getXXX();  
        test.getYYY();  
    }  
}
