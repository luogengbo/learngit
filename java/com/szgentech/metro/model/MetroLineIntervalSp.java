package com.szgentech.metro.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 区间沉降点
 * @author MAJL
 *
 */
public class MetroLineIntervalSp implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4888917865935677964L;
	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String leftOrRight;//左右线标记
	private String spName;//沉降点名称
	private Float originMileage;//初始里程
	private BigDecimal mapX;//大地坐标X
	private BigDecimal mapY;//大地坐标Y
	private Float spSumAdd;//累计沉降正值
	private Float spSumSub;//累计沉降负值
	private Float spSpeedWarningVal;//沉降速率预警值
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	
	public MetroLineIntervalSp() {}

	/**
	 * 获取originMileage
	 * @return originMileage originMileage
	 */
	public Float getOriginMileage() {
		return originMileage;
	}

	/**
	 * 设置originMileage
	 * @param originMileage originMileage 
	 */
	public void setOriginMileage(Float originMileage) {
		this.originMileage = originMileage;
	}

	/**
	 * 获取intervalId
	 * @return intervalId intervalId
	 */
	public Long getIntervalId() {
		return intervalId;
	}

	/**
	 * 设置intervalId
	 * @param intervalId intervalId 
	 */
	public void setIntervalId(Long intervalId) {
		this.intervalId = intervalId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public BigDecimal getMapX() {
		return mapX;
	}

	public void setMapX(BigDecimal mapX) {
		this.mapX = mapX;
	}

	public BigDecimal getMapY() {
		return mapY;
	}

	public void setMapY(BigDecimal mapY) {
		this.mapY = mapY;
	}

	public Float getSpSumAdd() {
		return spSumAdd;
	}

	public void setSpSumAdd(Float spSumAdd) {
		this.spSumAdd = spSumAdd;
	}

	public Float getSpSumSub() {
		return spSumSub;
	}

	public void setSpSumSub(Float spSumSub) {
		this.spSumSub = spSumSub;
	}

	public Float getSpSpeedWarningVal() {
		return spSpeedWarningVal;
	}

	public void setSpSpeedWarningVal(Float spSpeedWarningVal) {
		this.spSpeedWarningVal = spSpeedWarningVal;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLeftOrRight() {
		return leftOrRight;
	}

	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}

	@Override
	public String toString() {
		return "MetroLineIntervalSp [id=" + id + ", intervalId=" + intervalId + ", leftOrRight=" + leftOrRight
				+ ", spName=" + spName + ", originMileage=" + originMileage + ", mapX=" + mapX + ", mapY=" + mapY
				+ ", spSumAdd=" + spSumAdd + ", spSumSub=" + spSumSub + ", spSpeedWarningVal=" + spSpeedWarningVal
				+ ", updateTime=" + updateTime + ", createTime=" + createTime + "]";
	}

	
}
