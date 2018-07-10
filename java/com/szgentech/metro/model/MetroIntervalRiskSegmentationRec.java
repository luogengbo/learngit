package com.szgentech.metro.model;

import java.util.Date;
/**
 * 地质风险划分阻段划分
 * @author luohao
 *
 */
public class MetroIntervalRiskSegmentationRec implements java.io.Serializable{

	private static final long serialVersionUID =6927875150192921060L;
	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String leftOrRight;//左/右线标记 l左r右
	private Long riskSegmentationId; //风险id
	private Integer ringNum;//环号
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
	public Long getRiskSegmentationId() {
		return riskSegmentationId;
	}
	public void setRiskSegmentationId(Long riskSegmentationId) {
		this.riskSegmentationId = riskSegmentationId;
	}
	public Integer getRingNum() {
		return ringNum;
	}
	public void setRingNum(Integer ringNum) {
		this.ringNum = ringNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
