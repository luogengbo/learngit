package com.szgentech.metro.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 区间数据
 * @author MAJL
 *
 */
public class MetroLineIntervalData implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5927875150192921060L;
	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private Float mileage;//里程
	private BigDecimal mapX;//坐标X
	private BigDecimal mapY;//坐标Y
	private BigDecimal mapZ;//坐标Z
	private Float pitch;//坡度
	private String leftOrRight;//左/右线标记 l左r右
	private Integer isDel;//是否删除0否1是
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	
	public MetroLineIntervalData() {}

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

	public Float getMileage() {
		return mileage;
	}

	public void setMileage(Float mileage) {
		this.mileage = mileage;
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

	public BigDecimal getMapZ() {
		return mapZ;
	}

	public void setMapZ(BigDecimal mapZ) {
		this.mapZ = mapZ;
	}

	public Float getPitch() {
		return pitch;
	}

	public void setPitch(Float pitch) {
		this.pitch = pitch;
	}

	public String getLeftOrRight() {
		return leftOrRight;
	}

	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
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
	
	
}
