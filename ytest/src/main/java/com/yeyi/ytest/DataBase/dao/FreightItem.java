package com.yeyi.ytest.DataBase.dao;

public class FreightItem {
	private int id;
	private int ruleId;	// 运费规则 id
	private String itemType;
	private String theunit;		// P:件W:重量
	private String baseunit;	// 首件数量
	private String baseprice;	// 首件金额
	private String increaseunit;// 次件数量
	private String increaseprice;// 次件金额
	private Character isDefault;	// Y:默认  ； N:非默认，自定义模版
	private String showName;	// 自定义模版所选区域
	private String selectIds;	// 自定义模版所选区域ID
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getTheunit() {
		return theunit;
	}
	public void setTheunit(String theunit) {
		this.theunit = theunit;
	}
	public String getBaseunit() {
		return baseunit;
	}
	public void setBaseunit(String baseunit) {
		this.baseunit = baseunit;
	}
	public String getBaseprice() {
		return baseprice;
	}
	public void setBaseprice(String baseprice) {
		this.baseprice = baseprice;
	}
	public String getIncreaseunit() {
		return increaseunit;
	}
	public void setIncreaseunit(String increaseunit) {
		this.increaseunit = increaseunit;
	}
	public String getIncreaseprice() {
		return increaseprice;
	}
	public void setIncreaseprice(String increaseprice) {
		this.increaseprice = increaseprice;
	}
	public Character getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Character isDefault) {
		this.isDefault = isDefault;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getSelectIds() {
		return selectIds;
	}
	public void setSelectIds(String selectIds) {
		this.selectIds = selectIds;
	}
}
