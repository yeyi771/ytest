package com.yeyi.ytest.Mongo.dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="statistics_platform") // 这里绑定了哪使用哪个集合,如果没有这个注解则要在调用处指定 collectionName
public class StatisticsPlatformVO {

	private Integer pv;
	private Integer uv;
	private Integer year;
	private Integer month;
	private Integer day;
	private String date;
	
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getUv() {
		return uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
