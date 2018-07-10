package com.szgentech.metro.model;

import java.util.Date;
/**
 * 线路区间计划进度信息
 * @author luohao
 *
 */
public class MetroLineIntervalSchedule implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6615467204511260062L;
	private Long id;//系统ID
	private Long intervalId; //区间id
	private String leftOrRight;//左右线标记
	private Date scheduleDate;//计划日期
	private Integer scheduleRingNum;//左右计划日环数
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	private String cycleType;//周期类型
	private String cycleTitle;//
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public Integer getScheduleRingNum() {
		return scheduleRingNum;
	}
	public void setScheduleRingNum(Integer scheduleRingNum) {
		this.scheduleRingNum = scheduleRingNum;
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
	public String getCycleType() {
		return cycleType;
	}
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}
	public String getCycleTitle() {
		return cycleTitle;
	}
	public void setCycleTitle(String cycleTitle) {
		this.cycleTitle = cycleTitle;
	}
}
