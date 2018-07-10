package com.szgentech.metro.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitorIntervalLrStaticView {
	private String lineMark;// 工程名称
	private String date;// 统计日期
	private Float beginRing;// 开始环号
	private Float endRing;// 结束环号
	private Float ringNum;// 推进环数
	@JsonProperty
	private Float K0001;// 推进时间
	@JsonProperty
	private Float K0002;// 拼装时间
	@JsonProperty
	private Float K0003;// 停止时间
	@JsonProperty
	private Float D0023;// 同步注浆量
	@JsonProperty
	private Float G0001;// 盾尾油脂量
	private String beginDate;// 开始日期
	private String endDate;// 结束日期

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Float getBeginRing() {
		return beginRing;
	}

	public void setBeginRing(Float beginRing) {
		this.beginRing = beginRing;
	}

	public Float getEndRing() {
		return endRing;
	}

	public void setEndRing(Float endRing) {
		this.endRing = endRing;
	}

	public Float getRingNum() {
		return ringNum;
	}

	public void setRingNum(Float ringNum) {
		this.ringNum = ringNum;
	}

	@JsonIgnore
	public Float getK0001() {
		return K0001;
	}

	@JsonIgnore
	public void setK0001(Float k0001) {
		K0001 = k0001;
	}

	@JsonIgnore
	public Float getK0002() {
		return K0002;
	}

	@JsonIgnore
	public void setK0002(Float k0002) {
		K0002 = k0002;
	}

	@JsonIgnore
	public Float getK0003() {
		return K0003;
	}

	@JsonIgnore
	public void setK0003(Float k0003) {
		K0003 = k0003;
	}

	@JsonIgnore
	public Float getD0023() {
		return D0023;
	}

	@JsonIgnore
	public void setD0023(Float d0023) {
		D0023 = d0023;
	}

	@JsonIgnore
	public Float getG0001() {
		return G0001;
	}

	@JsonIgnore
	public void setG0001(Float g0001) {
		G0001 = g0001;
	}

	public String getLineMark() {
		return lineMark;
	}

	public void setLineMark(String lineMark) {
		this.lineMark = lineMark;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
