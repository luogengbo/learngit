package com.szgentech.metro.model;

import java.util.Date;
/**
 * 地质风险划分阻段划分
 * @author luohao
 *
 */
public class MetroIntervalRiskSegmentation implements java.io.Serializable{

	private static final long serialVersionUID =6927875150192921060L;
	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String leftOrRight;//左/右线标记 l左r右
	private String geologicNo;//地质组段编号
	private String geologicDescription;//隧道断面内地质组合描述
	private String hydrogeololgy;//地下水温埋深
	private String riskNo;//风险编号
	private String riskPoint;//风险点
	private String riskPhoto;//风险照片
	private Integer riskStartRing;//风险开始环
	private Integer riskEndRing;//风险结束环
	private Float startMileage;//风险开始里程
	private Float endMileage;//风险结束里程
	private Integer earlyWarningRing;//提前预警环数
	private String warningLevel;//预警等级
	private String riskDescription;//风险描述情况
	private String riskImg1Url;//风险图片1
	private String riskImg2Url;//风险图片2
	private String riskImg3Url;//风险图片3
	private String isUsed="1";//状态标记，1是有效，0是无效
	private Date updateTime;//更新时间
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
	public String getGeologicNo() {
		return geologicNo;
	}
	public void setGeologicNo(String geologicNo) {
		this.geologicNo = geologicNo;
	}
	public String getGeologicDescription() {
		return geologicDescription;
	}
	public void setGeologicDescription(String geologicDescription) {
		this.geologicDescription = geologicDescription;
	}
	public String getHydrogeololgy() {
		return hydrogeololgy;
	}
	public void setHydrogeololgy(String hydrogeololgy) {
		this.hydrogeololgy = hydrogeololgy;
	}
	public String getRiskNo() {
		return riskNo;
	}
	public void setRiskNo(String riskNo) {
		this.riskNo = riskNo;
	}
	public String getRiskPoint() {
		return riskPoint;
	}
	public void setRiskPoint(String riskPoint) {
		this.riskPoint = riskPoint;
	}
	public String getRiskPhoto() {
		return riskPhoto;
	}
	public void setRiskPhoto(String riskPhoto) {
		this.riskPhoto = riskPhoto;
	}
	public Integer getRiskStartRing() {
		return riskStartRing;
	}
	public void setRiskStartRing(Integer riskStartRing) {
		this.riskStartRing = riskStartRing;
	}
	public Integer getRiskEndRing() {
		return riskEndRing;
	}
	public void setRiskEndRing(Integer riskEndRing) {
		this.riskEndRing = riskEndRing;
	}
	public Float getStartMileage() {
		return startMileage;
	}
	public void setStartMileage(Float startMileage) {
		this.startMileage = startMileage;
	}
	public Float getEndMileage() {
		return endMileage;
	}
	public void setEndMileage(Float endMileage) {
		this.endMileage = endMileage;
	}
	public Integer getEarlyWarningRing() {
		return earlyWarningRing;
	}
	public void setEarlyWarningRing(Integer earlyWarningRing) {
		this.earlyWarningRing = earlyWarningRing;
	}
	public String getWarningLevel() {
		return warningLevel;
	}
	public void setWarningLevel(String warningLevel) {
		this.warningLevel = warningLevel;
	}
	public String getRiskDescription() {
		return riskDescription;
	}
	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}
	public String getRiskImg1Url() {
		return riskImg1Url;
	}
	public void setRiskImg1Url(String riskImg1Url) {
		this.riskImg1Url = riskImg1Url;
	}
	public String getRiskImg2Url() {
		return riskImg2Url;
	}
	public void setRiskImg2Url(String riskImg2Url) {
		this.riskImg2Url = riskImg2Url;
	}
	public String getRiskImg3Url() {
		return riskImg3Url;
	}
	public void setRiskImg3Url(String riskImg3Url) {
		this.riskImg3Url = riskImg3Url;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
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
