package com.szgentech.metro.model;

import java.util.List;

/**
 * 监测数据实体类
 *
 */
public class MonitorIntervalData implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 1210018463030234884L;
	
	private String spName; //沉降点名称
	private List<MonitorIntervalSettlementPoint> dataPoints;
	
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public List<MonitorIntervalSettlementPoint> getDataPoints() {
		return dataPoints;
	}
	public void setDataPoints(List<MonitorIntervalSettlementPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}
	
	
}
