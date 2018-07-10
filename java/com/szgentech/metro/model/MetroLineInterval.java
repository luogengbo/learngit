package com.szgentech.metro.model;

import java.util.Date;
import java.util.List;

/**
 * 线路区间
 * 
 * @author MAJL
 *
 */
public class MetroLineInterval implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2009337539319164592L;
	private Long id;// 系统ID
	private Long lineId;// 线路id
	private String intervalName;// 区间名称
	private Integer intervalMark;// 工程标号
	private Integer status;// 状态控制区间在工程树中是否可见（0禁用1启用）
	private String projectPdfUrl;// 工程文档URL
	private String imgMapXyUrl;// 剖面图大地坐标文件URL
	private String riskPdfUrl;// 风险组段划分URL
	private String remark;// 备注
	private Date updateTime;// 更新时间
	private Date createTime;// 创建时间
	private List<MetroLineIntervalData> intervalDataList;// 线路区间左右线数据列表
	private List<MetroLineIntervalLr> intervalLrList;// 左右线信息
	private Long intervalId;// 区间id用于地图
	private Long ductIntervalId;// 管片ID
	private String surveyPdfUrl; // 地质详勘
	private String parameterPdfUrl; // 盾构机技术参数表URL

	public String getSurveyPdfUrl() {
		return surveyPdfUrl;
	}

	public void setSurveyPdfUrl(String surveyPdfUrl) {
		this.surveyPdfUrl = surveyPdfUrl;
	}

	public Long getDuctIntervalId() {
		return ductIntervalId;
	}

	public void setDuctIntervalId(Long ductIntervalId) {
		this.ductIntervalId = ductIntervalId;
	}

	public MetroLineInterval() {
	}

	/**
	 * 获取id
	 * 
	 * @return id id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 *            id
	 */
	public void setId(Long id) {
		this.id = id;
		this.intervalId = id;
	}

	/**
	 * 获取lineId
	 * 
	 * @return lineId lineId
	 */
	public Long getLineId() {
		return lineId;
	}

	/**
	 * 设置lineId
	 * 
	 * @param lineId
	 *            lineId
	 */
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	/**
	 * 获取status
	 * 
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置status
	 * 
	 * @param status
	 *            status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取projectPdfUrl
	 * 
	 * @return projectPdfUrl projectPdfUrl
	 */
	public String getProjectPdfUrl() {
		return projectPdfUrl;
	}

	/**
	 * 设置projectPdfUrl
	 * 
	 * @param projectPdfUrl
	 *            projectPdfUrl
	 */
	public void setProjectPdfUrl(String projectPdfUrl) {
		this.projectPdfUrl = projectPdfUrl;
	}

	/**
	 * 获取imgMapXyUrl
	 * 
	 * @return imgMapXyUrl
	 */
	public String getImgMapXyUrl() {
		return imgMapXyUrl;
	}

	/**
	 * 设置imgMapXyUrl
	 * 
	 * @param imgMapXyUrl
	 *            imgMapXyUrl
	 */
	public void setImgMapXyUrl(String imgMapXyUrl) {
		this.imgMapXyUrl = imgMapXyUrl;
	}

	/**
	 * 获取riskPdfUrl
	 * 
	 * @return riskPdfUrl
	 */
	public String getRiskPdfUrl() {
		return riskPdfUrl;
	}

	/**
	 * 设置riskPdfUrl
	 * 
	 * @param riskPdfUrl
	 *            riskPdfUrl
	 */
	public void setRiskPdfUrl(String riskPdfUrl) {
		this.riskPdfUrl = riskPdfUrl;
	}

	/**
	 * 获取remark
	 * 
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置remark
	 * 
	 * @param remark
	 *            remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取updateTime
	 * 
	 * @return updateTime
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

	/**
	 * 获取createTime
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置createTime
	 * 
	 * @param createTime
	 *            createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	/**
	 * 获取intervalDataList
	 * 
	 * @return intervalDataList
	 */
	public List<MetroLineIntervalData> getIntervalDataList() {
		return intervalDataList;
	}

	/**
	 * 设置intervalDataList
	 * 
	 * @param intervalDataList
	 *            intervalDataList
	 */
	public void setIntervalDataList(List<MetroLineIntervalData> intervalDataList) {
		this.intervalDataList = intervalDataList;
	}

	/**
	 * 获取intervalLrList
	 * 
	 * @return intervalLrList
	 */
	public List<MetroLineIntervalLr> getIntervalLrList() {
		return intervalLrList;
	}

	public void setIntervalLrList(List<MetroLineIntervalLr> intervalLrList) {
		this.intervalLrList = intervalLrList;
	}

	public Long getIntervalId() {
		return intervalId;
	}

	public void setIntervalId(Long intervalId) {
		this.intervalId = intervalId;
	}

	public Integer getIntervalMark() {
		return intervalMark;
	}

	public void setIntervalMark(Integer intervalMark) {
		this.intervalMark = intervalMark;
	}

	public String getParameterPdfUrl() {
		return parameterPdfUrl;
	}

	public void setParameterPdfUrl(String parameterPdfUrl) {
		this.parameterPdfUrl = parameterPdfUrl;
	}

}
