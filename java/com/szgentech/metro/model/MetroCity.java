package com.szgentech.metro.model;

import java.util.Date;
import java.util.List;

/**
 * 城市
 * 
 * @author MAJL
 *
 */
public class MetroCity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2336392866189869216L;
	private Long id; // 系统ID
	private String cityName; // 城市名称
	private String projectPdfUrl; // 工程文档url
	private String projectTitle; // 工程名称
	private Date updateTime; // 修改时间
	private Date createTime; // 创建时间
	private List<MetroLine> lineList;// 城市地铁线路

	public MetroCity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProjectPdfUrl() {
		return projectPdfUrl;
	}

	public void setProjectPdfUrl(String projectPdfUrl) {
		this.projectPdfUrl = projectPdfUrl;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	/**
	 * 获取updateTime
	 * 
	 * @return updateTime updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置updateTime
	 * 
	 * @param updateTime
	 *            updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<MetroLine> getLineList() {
		return lineList;
	}

	public void setLineList(List<MetroLine> lineList) {
		this.lineList = lineList;
	}

}
