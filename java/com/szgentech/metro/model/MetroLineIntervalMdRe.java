package com.szgentech.metro.model;

import java.util.Date;

/**
 * 沉降点监测记录文件上传记录
 * @author MAJL
 *
 */
public class MetroLineIntervalMdRe implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4327094729007442732L;
	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String fileName;//文件名称
	private String originFileName;//原文件名
	private Date monitorDate;//监测日期
	private String fileUrl;//文件URL
	private Integer isDel;//是否删除0否1是
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	   
	public MetroLineIntervalMdRe() {
	}

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
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

	/**
	 * 获取originFileName
	 * @return originFileName originFileName
	 */
	public String getOriginFileName() {
		return originFileName;
	}

	/**
	 * 设置originFileName
	 * @param originFileName originFileName 
	 */
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	
	
}
