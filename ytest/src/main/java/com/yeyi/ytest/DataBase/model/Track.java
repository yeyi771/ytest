package com.yeyi.ytest.DataBase.model;

public class Track {
	private int id;
	private String jid;
	private String lastVisit;
	private String fromType;
	private String fromId;
	private String uploaderJid;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUploaderJid() {
		return uploaderJid;
	}
	public void setUploaderJid(String uploaderJid) {
		this.uploaderJid = uploaderJid;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public String getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(String lastVisit) {
		this.lastVisit = lastVisit;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
}
