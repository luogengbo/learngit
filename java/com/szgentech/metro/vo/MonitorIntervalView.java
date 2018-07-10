package com.szgentech.metro.vo;

import java.util.List;

@SuppressWarnings("rawtypes")
public class MonitorIntervalView {
	private String title;// 标题 [二号线-[15标]-公园前区间]监测数据曲线
	private Long intervalSpId;//
	private List<String> dataTime;// ["2014-09-13 19:33:44","2014-09-13
									// 19:33:44","2014-09-13 19:33:44"]
	private List<List> grandSettlement;// [[-14,-3],[-13,3.0],[-12,5.0]]
	private List<List> speedSettlement;// [[-14,-3],[-13,3.0],[-12,5.0]]
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getIntervalSpId() {
		return intervalSpId;
	}

	public void setIntervalSpId(Long intervalSpId) {
		this.intervalSpId = intervalSpId;
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
