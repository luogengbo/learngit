package com.szgentech.metro.model;

import java.util.Date;

/**
 * 监测预警记录
 * @author MAJL
 *
 */
public class MetroLineIntervalWarningRec implements java.io.Serializable{
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = -6454460108791982193L;
	private Long id;//系统ID
    private Long intervalId;//区间ID
    private String leftOrRight;//左/右线标记 l左 r右
    private String paramName;//参数名称 盾构字典表字段名
    private Float numValue;//数值
    private Integer warningLevel;//预警级别1红色上限预警2橙色上限预警3橙色下限预警4红色下限预警
    private Integer ringNum;//环号
    private Float redWarningMax;//红色预警上限
    private Float orangeWarningMax;//橙色预警上限
    private Float orangeWarningMin;//橙色预警下限
    private Float redWarningMin;//红色预警下限
    private Date warningTime;//预警时间
    private Date updateTime;//修改时间
    private Date createTime;//创建时间
    private Long confirmLevel;//是否已人工确认等级0否1是
    private Long isPush;//是否已推送0否1是
    private MetroDictionary dic;//参数名称 盾构字典表信息
    private Float yellowWarningMax; //黄色预警上限
	private Float yellowWarningMin; //黄色预警下限
	private String intervalName; //区间名称
    
    public MetroLineIntervalWarningRec(){}
    
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
	public Integer getWarningLevel() {
		return warningLevel;
	}
	public void setWarningLevel(Integer warningLevel) {
		this.warningLevel = warningLevel;
	}
	public Integer getRingNum() {
		return ringNum;
	}
	public void setRingNum(Integer ringNum) {
		this.ringNum = ringNum;
	}
	public Float getRedWarningMax() {
		return redWarningMax;
	}
	public void setRedWarningMax(Float redWarningMax) {
		this.redWarningMax = redWarningMax;
	}
	public Float getOrangeWarningMax() {
		return orangeWarningMax;
	}
	public void setOrangeWarningMax(Float orangeWarningMax) {
		this.orangeWarningMax = orangeWarningMax;
	}
	public Float getOrangeWarningMin() {
		return orangeWarningMin;
	}
	public void setOrangeWarningMin(Float orangeWarningMin) {
		this.orangeWarningMin = orangeWarningMin;
	}
	public Float getRedWarningMin() {
		return redWarningMin;
	}
	public void setRedWarningMin(Float redWarningMin) {
		this.redWarningMin = redWarningMin;
	}
	public Date getWarningTime() {
		return warningTime;
	}
	public void setWarningTime(Date warningTime) {
		this.warningTime = warningTime;
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

	public MetroDictionary getDic() {
		return dic;
	}

	public void setDic(MetroDictionary dic) {
		this.dic = dic;
	}

	public Long getConfirmLevel() {
		return confirmLevel;
	}

	public void setConfirmLevel(Long confirmLevel) {
		this.confirmLevel = confirmLevel;
	}

	public Long getIsPush() {
		return isPush;
	}

	public void setIsPush(Long isPush) {
		this.isPush = isPush;
	}

	public Float getYellowWarningMax() {
		return yellowWarningMax;
	}

	public void setYellowWarningMax(Float yellowWarningMax) {
		this.yellowWarningMax = yellowWarningMax;
	}

	public Float getYellowWarningMin() {
		return yellowWarningMin;
	}

	public void setYellowWarningMin(Float yellowWarningMin) {
		this.yellowWarningMin = yellowWarningMin;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}
    
    
}
