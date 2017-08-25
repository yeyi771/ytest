package com.yeyi.ytest;

class PostClass{
	boolean b;
	int i;
	boolean isMajor;
	boolean issMajor;
	String str;

	@Override
    public String toString() {
        return "PostClass [b=" + b + ", i=" + i + ", isMajor=" + isMajor + ", issMajor=" + issMajor + ", str=" + str + "]";
    }
    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }
    public boolean isB() {
		return b;
	}
	public void setB(boolean b) {
		this.b = b;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public boolean isMajor() {
		return isMajor;
	}
	// 注意这里 eclipse 自动生成的是 setMajor，会导致 PostClass 作为 Controller 的参数时取不到request中正确的值
	public void setIsMajor(boolean isMajor) {
		this.isMajor = isMajor;
	}
	public boolean isIssMajor() {
		return issMajor;
	}
	public void setIssMajor(boolean issMajor) {
		this.issMajor = issMajor;
	}
	
}
