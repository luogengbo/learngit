package com.szgentech.metro.vo;

import java.util.Date;

public class DataChild implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -228505870678101807L;
	
    private int currentRing;//当前环数
    private int buildStatus;//
    private Date dataTime;
    private int advanceStatus;//取
    private int communiStatus;//通信状态
    private String alarm;//[红色预警] 土压 2.5 bar
    
	public int getCurrentRing() {
		return currentRing;
	}
	public void setCurrentRing(int currentRing) {
		this.currentRing = currentRing;
	}
	public int getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(int buildStatus) {
		this.buildStatus = buildStatus;
	}
	public Date getDataTime() {
		return dataTime;
	}
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
	public int getAdvanceStatus() {
		return advanceStatus;
	}
	public void setAdvanceStatus(int advanceStatus) {
		this.advanceStatus = advanceStatus;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getCommuniStatus() {
		return communiStatus;
	}

	public void setCommuniStatus(int communiStatus) {
		this.communiStatus = communiStatus;
	}

	public String getAlarm() {
		return alarm;
	}
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}      
}
