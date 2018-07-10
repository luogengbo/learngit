package com.szgentech.metro.model;

import java.util.List;

public class MonitorIntervalDataView {

	private String title; // 标题 [二号线-[15标]-公园前区间]监测数据曲线

	private List<MonitorInterDataViewItem> dataViewItems; // 一个沉降点一条曲线
	private MonitorInterDataViewPoint dataViewPoint; // 多个沉降点在同一曲线

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MonitorInterDataViewItem> getDataViewItems() {
		return dataViewItems;
	}

	public void setDataViewItems(List<MonitorInterDataViewItem> dataViewItems) {
		this.dataViewItems = dataViewItems;
	}

	public MonitorInterDataViewPoint getDataViewPoint() {
		return dataViewPoint;
	}

	public void setDataViewPoint(MonitorInterDataViewPoint dataViewPoint) {
		this.dataViewPoint = dataViewPoint;
	}

}
