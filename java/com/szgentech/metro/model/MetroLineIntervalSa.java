package com.szgentech.metro.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 管片姿态数据
 * 
 * @author wangcan
 *
 */
public class MetroLineIntervalSa implements Serializable {

	private static final long serialVersionUID = 963039072275952619L;

	private Long id;// 系统ID
	private Long intervalId; // 线路区间id
	private String leftOrRight;// 左右线标记
	private String ringNum;// 环号
	private Float horizontalDev;// 水平偏差
	private Float verticalDev;// 垂直偏差
	private Date dateTime;// 日期时间
	private Date updateTime;// 更新时间

	public MetroLineIntervalSa() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取intervalId
	 * 
	 * @return intervalId intervalId
	 */
	public Long getIntervalId() {
		return intervalId;
	}

	public void setIntervalId(Long intervalId) {
		this.intervalId = intervalId;
	}

	public String getLeftOrRight() {
		return leftOrRight;
	}

	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}

	public String getRingNum() {
		return ringNum;
	}

	public void setRingNum(String ringNum) {
		this.ringNum = ringNum;
	}

	public Float getHorizontalDev() {
		return horizontalDev;
	}

	public void setHorizontalDev(Float horizontalDev) {
		this.horizontalDev = horizontalDev;
	}

	public Float getVerticalDev() {
		return verticalDev;
	}

	public void setVerticalDev(Float verticalDev) {
		this.verticalDev = verticalDev;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
