package com.szgentech.metro.model;

import java.util.Date;
/**
 * 用户数据权限
 * @author MAJL
 *
 */
public class MetroUserDataRel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4680459940430288289L;
	private Long id;//系统ID
	private Long userId;//用户ID来源于用户表系统ID
	private Long cityId;//城市ID来源于城市表系统ID
	private Long lineId;//地铁线路ID来源于线路表系统ID
	private Long intervalId;//线路区间ID来源于线路区间表系统ID
	private String leftOrRight;//左/右线标记l左r右
	private Date createTime;//创建时间
	
	public MetroUserDataRel(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
