package com.yeyi.YTool;

public class NumberUtil {
	
	/**
	 * 判断 d 是否是一个整数
	 * @param d
	 * @param accuracy 精度,即此与最接近整数的差小于时值算是整数。
	 * @return
	 */
	public static boolean isInteger(double d, double accuracy){
		return (Math.abs(Math.floor(d) - d) < accuracy); 
	}
	
	/**
	 * 判断 d 是否是一个整数,精度 0.00001
	 */
	public static boolean isInteger(double d){
		return isInteger(d,0.00001);
	}
	
	public static void main(String [] argv){
		System.out.println(isInteger(0.0001));
		System.out.println(isInteger(0.00001));
		System.out.println(isInteger(0.000001));
// output:
//		false
//		false
//		true
	}
}
