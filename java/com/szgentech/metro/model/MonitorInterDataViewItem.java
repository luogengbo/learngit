package com.szgentech.metro.model;

import java.util.List;

public class MonitorInterDataViewItem {
	
	private String spName;// 沉降点名称
	private List<String> dataTime;// ["2014-09-13 19:33:44","2014-09-13
									// 19:33:44","2014-09-13 19:33:44"]
	private List<List> grandSettlement;// [[-14,-3],[-13,3.0],[-12,5.0]]
	private List<List> speedSettlement;// [[-14,-3],[-13,3.0],[-12,5.0]]
	
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public List<String> getDataTime() {
		return dataTime;
	}
	public void setDataTime(List<String> dataTime) {
		this.dataTime = dataTime;
	}
	public List<List> getGrandSettlement() {
		return grandSettlement;
	}
	public void setGrandSettlement(List<List> grandSettlement) {
		this.grandSettlement = grandSettlement;
	}
	public List<List> getSpeedSettlement() {
		return speedSettlement;
	}
	public void setSpeedSettlement(List<List> speedSettlement) {
		this.speedSettlement = speedSettlement;
	}
	
	
}
