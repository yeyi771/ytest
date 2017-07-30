package com.yeyi.test;

interface Int1{
    int getInt();
    String getStr();
}

interface Int2{
    int getInt();
    char getChar();
}

/**
 * 同时继承有多个接口，接口有相同函数签名也可以正常使用（ getInt()）
 */
public class InterfaceTest implements Int1, Int2{

    @Override
    public int getInt() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getStr() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char getChar() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
