package com.szgentech.metro.model;

import java.util.Date;
/**
 * 用户与风险点的关系
 * @author luohao
 *
 */
public class MetroUserRiskSegmentationRel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4680459940430288989L;
	private Long id;//系统ID
	private Long userId;//用户ID来源于用户表系统ID
	private Long riskSegmentationId;//风险ID
	private String isUsed="1";//是否启用0否1是
	private String phoneNo; //联系电话
	private Date createTime;//创建时间
	public MetroUserRiskSegmentationRel(){}
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
	public Long getRiskSegmentationId() {
		return riskSegmentationId;
	}
	public void setRiskSegmentationId(Long riskSegmentationId) {
		this.riskSegmentationId = riskSegmentationId;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
