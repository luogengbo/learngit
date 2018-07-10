package com.szgentech.metro.model;

public class MonitorIntervalSettlementPoint  implements java.io.Serializable {
	
	private static final long serialVersionUID = -7058279420262531201L;
	
	private String spName; //沉降点名称
	private float thisVar; //本次变化量	
	private float sumVar; //累计变化量
	private float originMileage; //初始里程
	private String monitorDate; //监测日期
	private float spSpeed;  //沉降点速录
	
	
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public float getThisVar() {
		return thisVar;
	}
	public void setThisVar(float thisVar) {
		this.thisVar = thisVar;
	}
	public float getSumVar() {
		return sumVar;
	}
	public void setSumVar(float sumVar) {
		this.sumVar = sumVar;
	}
	public float getOriginMileage() {
		return originMileage;
	}
	public void setOriginMileage(float originMileage) {
		this.originMileage = originMileage;
	}
	public String getMonitorDate() {
		return monitorDate;
	}
	public void setMonitorDate(String monitorDate) {
		this.monitorDate = monitorDate;
	}
	public float getSpSpeed() {
		return spSpeed;
	}
	public void setSpSpeed(float spSpeed) {
		this.spSpeed = spSpeed;
	}
	@Override
	public String toString() {
		return "MonitorIntervalSettlementPoint [spName=" + spName + ", thisVar=" + thisVar + ", sumVar=" + sumVar
				+ ", originMileage=" + originMileage + ", monitorDate=" + monitorDate + ", spSpeed=" + spSpeed + "]";
	}
	
	

}
