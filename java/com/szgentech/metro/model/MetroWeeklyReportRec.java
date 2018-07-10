package com.szgentech.metro.model;

import java.util.Date;
/**
 * 周报表上传记录
 * @author luohao
 *
 */
public class MetroWeeklyReportRec implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1859422776135918628L;

	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String leftOrRight;//左/右线标记 l左r右
	private String reportName; //报表文件名
	private String reportTime; //报表日期
	private String reportUrl;//报表URL
	private String uploadPeople;//上传人
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
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getReportUrl() {
		return reportUrl;
	}
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	public String getUploadPeople() {
		return uploadPeople;
	}
	public void setUploadPeople(String uploadPeople) {
		this.uploadPeople = uploadPeople;
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
