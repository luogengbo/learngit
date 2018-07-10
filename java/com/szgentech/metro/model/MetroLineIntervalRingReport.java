package com.szgentech.metro.model;

import java.util.Date;

/**
 * 环报表基础数据
 * @author luohao
 *
 */
public class MetroLineIntervalRingReport implements java.io.Serializable{
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = -6454460108791982198L;
	private Long id;//系统ID
    private Long intervalId;//区间ID
    private String leftOrRight;//左/右线标记 l左 r右
    private Integer ringNum;//环号
    private Float Stroke;//推进行程
    private String paramName;//参数名称 盾构字典表字段名
    private Float numValue;//数值
    private Date jobTime;//作业时间
    private Date updateTime;//修改时间
    private Date createTime;//创建时间
    
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
	public Integer getRingNum() {
		return ringNum;
	}
	public void setRingNum(Integer ringNum) {
		this.ringNum = ringNum;
	}
	public Float getStroke() {
		return Stroke;
	}
	public void setStroke(Float stroke) {
		Stroke = stroke;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public Float getNumValue() {
		return numValue;
	}
	public void setNumValue(Float numValue) {
		this.numValue = numValue;
	}
	public Date getJobTime() {
		return jobTime;
	}
	public void setJobTime(Date jobTime) {
		this.jobTime = jobTime;
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
