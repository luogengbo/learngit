package com.szgentech.metro.model;

import java.util.Date;

public class MonitorInfoCity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8287307440408089951L;
	
	private String cityName;//城市名称
	private String lineName;//线路名称
	private Integer lineNo;//线路编号
	private String intervalName;//区间名称
	private Integer intervalMark;//区间标号
	private Integer buildStatus;//工程状态
	private String leftOrRight;//左右线标记
	private Float ringNum;//总环数
	private String machineNo;//盾构机编号
	private Date buildDate;//施工日期(始发日期)
	private Date throughDate;//结束日期
	private Integer speedQualifiedRange;//日掘进速度合格范围
	private String machineContractor; //施工单位
	private String direction; //推进方向，0是大里程从小往大的推进，1是小里程从大往小的推进
	private Date time;//更新时间
	
	private int machineType;//盾构机类型；
	private Integer intervalId;//区间ID
	
	public String getMachineContractor() {
		return machineContractor;
	}
	public void setMachineContractor(String machineContractor) {
		this.machineContractor = machineContractor;
	}
	public Date getThroughDate() {
		return throughDate;
	}
	public void setThroughDate(Date throughDate) {
		this.throughDate = throughDate;
	}
	public int getMachineType() {
		return machineType;
	}
	public void setMachineType(int machineType) {
		this.machineType = machineType;
	}
	public Integer getIntervalId() {
		return intervalId;
	}
	public void setIntervalId(Integer intervalId) {
		this.intervalId = intervalId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Integer getLineNo() {
		return lineNo;
	}
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	public Integer getIntervalMark() {
		return intervalMark;
	}
	public void setIntervalMark(Integer intervalMark) {
		this.intervalMark = intervalMark;
	}
	public Integer getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(Integer buildStatus) {
		this.buildStatus = buildStatus;
	}
	public String getLeftOrRight() {
		return leftOrRight;
	}
	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}
	public String getIntervalName() {
		return intervalName;
	}
	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}
	public Float getRingNum() {
		return ringNum;
	}
	public void setRingNum(Float ringNum) {
		this.ringNum = ringNum;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	
	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	public Integer getSpeedQualifiedRange() {
		return speedQualifiedRange;
	}
	public void setSpeedQualifiedRange(Integer speedQualifiedRange) {
		this.speedQualifiedRange = speedQualifiedRange;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
