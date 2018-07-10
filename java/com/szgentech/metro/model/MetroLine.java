package com.szgentech.metro.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 地铁线路
 * @author MAJL
 *
 */
public class MetroLine implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6447668793584724293L;
	private Long id;//系统ID
	private Long cityId; //城市id
	private Integer lineNo;//线路编号
	private String lineName;//工程名称
	private String lineColor;//标记颜色 颜色编码
	private Integer lineStatus;//状态0禁用1启用
	private BigDecimal mapX;//地图标记坐标X
	private BigDecimal mapY;//地图标记坐标Y
	private String projectPdfUrl;//工程文档URL
	private String remark;//备注
	private Date updateTime;//修改时间
	private Date createTime;//创建时间
	private List<MetroLineInterval> intervalList;//线路区间
	
	public MetroLine(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getLineColor() {
		return lineColor;
	}

	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	public Integer getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(Integer lineStatus) {
		this.lineStatus = lineStatus;
	}

	public BigDecimal getMapX() {
		return mapX;
	}

	public void setMapX(BigDecimal mapX) {
		this.mapX = mapX;
	}

	public BigDecimal getMapY() {
		return mapY;
	}

	public void setMapY(BigDecimal mapY) {
		this.mapY = mapY;
	}

	public String getProjectPdfUrl() {
		return projectPdfUrl;
	}

	public void setProjectPdfUrl(String projectPdfUrl) {
		this.projectPdfUrl = projectPdfUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<MetroLineInterval> getIntervalList() {
		return intervalList;
	}

	public void setIntervalList(List<MetroLineInterval> intervalList) {
		this.intervalList = intervalList;
	}

	/**
	 * 获取cityId
	 * @return cityId cityId
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * 设置cityId
	 * @param cityId cityId 
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	
	
}
