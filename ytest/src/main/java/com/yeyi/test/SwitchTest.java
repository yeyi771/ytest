package com.yeyi.test;

public class SwitchTest {
    public static void main(String[] args) {
//        Long long_m = 0L;
//        // 编译错误，Long类型不能用于switch语句
//        // Cannot switch on a value of type Long. Only convertible int values, strings or enum variables are permitted
//        switch (long_m) {
//            case 0:
//                System.out.println("Long类可以用于switch语句");
//                System.out.println();
//                break;
//        }
        
        // String对象
        String string_s = "Z";
        // 编译错误，String型可以用于switch语句(旧版本是不行的，但不知道是从哪个版本开始的)
        switch (string_s) {
            case "Z":
                System.out.println("String可以用于switch语句");
                System.out.println();
                break;
        }
    }
}
