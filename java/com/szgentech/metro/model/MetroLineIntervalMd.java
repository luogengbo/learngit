package com.szgentech.metro.model;

import java.util.Date;

/**
 * 沉降点监测数据
 * @author MAJL
 *
 */
public class MetroLineIntervalMd implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1135972117843954449L;
	private Long id;//系统ID
	private Long mdReId;//上传记录id
	private String mdNo;//编号也可称作沉降点名称
	private Float startElevation;//初始高程
	private Float upnoceElevation;//前次高程
	private Float thisElevation;//本次高程
	private Float thisVar;//本次变化量
	private Float sumVar;//累计变化量
	private	Float mileage;//里程
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	
	private Date monitorDate;//监测时间
	private Float spSpeed;//沉降速率
	
	
	public MetroLineIntervalMd() {
	}

	/**
	 * 获取mdReId
	 * @return mdReId mdReId
	 */
	public Long getMdReId() {
		return mdReId;
	}

	/**
	 * 设置mdReId
	 * @param mdReId mdReId 
	 */
	public void setMdReId(Long mdReId) {
		this.mdReId = mdReId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMdNo() {
		return mdNo;
	}

	public void setMdNo(String mdNo) {
		this.mdNo = mdNo;
	}

	public Float getStartElevation() {
		return startElevation;
	}

	public void setStartElevation(Float startElevation) {
		this.startElevation = startElevation;
	}

	public Float getUpnoceElevation() {
		return upnoceElevation;
	}

	public void setUpnoceElevation(Float upnoceElevation) {
		this.upnoceElevation = upnoceElevation;
	}

	public Float getThisElevation() {
		return thisElevation;
	}

	public void setThisElevation(Float thisElevation) {
		this.thisElevation = thisElevation;
	}

	public Float getThisVar() {
		return thisVar;
	}

	public void setThisVar(Float thisVar) {
		this.thisVar = thisVar;
	}

	public Float getSumVar() {
		return sumVar;
	}

	public void setSumVar(Float sumVar) {
		this.sumVar = sumVar;
	}

	public Float getMileage() {
		return mileage;
	}

	public void setMileage(Float mileage) {
		this.mileage = mileage;
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

	public Date getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}

	public Float getSpSpeed() {
		return spSpeed;
	}

	public void setSpSpeed(Float spSpeed) {
		this.spSpeed = spSpeed;
	}
}
