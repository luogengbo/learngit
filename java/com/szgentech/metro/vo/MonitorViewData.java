package com.szgentech.metro.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 城市列表数据VO
 * 
 * @author MAJL
 *
 */
public class MonitorViewData {
	private String intervalName; // 区间名称
	private String leftOrRight; // 左右线标记
	private Integer A0002; // 工作状态
	private Float A0001; // 当前环数
	private int communiStatus; // 通信状态
	private Float totalRingNum; // 总环数
	private Float B0006; // 总推力
	private Float A0004; // 土压上
	private Float B0004; // 刀盘扭矩
	private Float B0001; // 推进速度
	private Float B0002; // 刀盘转速
	private Float D0023; // 注浆总量
	private Integer buildStatus; // 工程状态

	private Integer intervalId; //区间id
	private String lineName;  //线路名称

	private Date buildDate;//施工日期(始发日期)
	private Date throughDate;//结束日期
	
	
	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public Date getThroughDate() {
		return throughDate;
	}

	public void setThroughDate(Date throughDate) {
		this.throughDate = throughDate;
	}

	public Integer getIntervalId() {
		return intervalId;
	}

	public void setIntervalId(Integer intervalId) {
		this.intervalId = intervalId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	public String getLeftOrRight() {
		return leftOrRight;
	}

	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}

	@JsonProperty("A0002")
	public Integer getA0002() {
		return A0002;
	}

	public void setA0002(Integer a0002) {
		this.A0002 = a0002;
	}

	@JsonProperty("A0001")
	public Float getA0001() {
		return A0001;
	}

	public void setA0001(Float a0001) {
		this.A0001 = a0001;
	}

	public Float getTotalRingNum() {
		return totalRingNum;
	}

	public void setTotalRingNum(Float totalRingNum) {
		this.totalRingNum = totalRingNum;
	}

	@JsonProperty("B0006")
	public Float getB0006() {
		return B0006;
	}

	public void setB0006(Float b0006) {
		this.B0006 = b0006;
	}

	@JsonProperty("A0004")
	public Float getA0004() {
		return A0004;
	}

	public void setA0004(Float a0004) {
		this.A0004 = a0004;
	}

	@JsonProperty("B0004")
	public Float getB0004() {
		return B0004;
	}

	public void setB0004(Float b0004) {
		this.B0004 = b0004;
	}

	@JsonProperty("B0001")
	public Float getB0001() {
		return B0001;
	}

	public void setB0001(Float b0001) {
		this.B0001 = b0001;
	}

	@JsonProperty("B0002")
	public Float getB0002() {
		return B0002;
	}

	public void setB0002(Float b0002) {
		this.B0002 = b0002;
	}

	@JsonProperty("D0023")
	public Float getD0023() {
		return D0023;
	}

	public void setD0023(Float d0023) {
		this.D0023 = d0023;
	}

	public Integer getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(Integer buildStatus) {
		this.buildStatus = buildStatus;
	}

	public int getCommuniStatus() {
		return communiStatus;
	}

	public void setCommuniStatus(int communiStatus) {
		this.communiStatus = communiStatus;
	}
}
