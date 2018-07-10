package com.szgentech.metro.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程进度
 * @author MAJL
 *
 */
public class MetroLineIntervalRp implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 167154597545519960L;

	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String positionNo;//位置标识码
	private String leftOrRight;//左/右线
	private String gType;//工程图
	private String textMsg;//文本信息
	private String riskImgUrl;//图片
	private String riskPdf1Url;//文档1
	private String riskPdf2Url;//文档2
	private String riskPdf3Url;//文档3
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	private BigDecimal riskX;//新增风险坐标X
	private BigDecimal riskY;//新增风险坐标Y
	private BigDecimal riskWidth;//新增风险位置宽
	private BigDecimal riskHight;//新增风险位置高

	public MetroLineIntervalRp() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	public String getLeftOrRight() {
		return leftOrRight;
	}

	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}

	public String getgType() {
		return gType;
	}

	public void setgType(String gType) {
		this.gType = gType;
	}

	public String getTextMsg() {
		return textMsg;
	}

	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}

	public String getRiskImgUrl() {
		return riskImgUrl;
	}

	public void setRiskImgUrl(String riskImgUrl) {
		this.riskImgUrl = riskImgUrl;
	}

	public String getRiskPdf1Url() {
		return riskPdf1Url;
	}

	public void setRiskPdf1Url(String riskPdf1Url) {
		this.riskPdf1Url = riskPdf1Url;
	}

	public String getRiskPdf2Url() {
		return riskPdf2Url;
	}

	public void setRiskPdf2Url(String riskPdf2Url) {
		this.riskPdf2Url = riskPdf2Url;
	}

	public String getRiskPdf3Url() {
		return riskPdf3Url;
	}

	public void setRiskPdf3Url(String riskPdf3Url) {
		this.riskPdf3Url = riskPdf3Url;
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

	public BigDecimal getRiskX() {
		return riskX;
	}

	public void setRiskX(BigDecimal riskX) {
		this.riskX = riskX;
	}

	public BigDecimal getRiskY() {
		return riskY;
	}

	public void setRiskY(BigDecimal riskY) {
		this.riskY = riskY;
	}

	public BigDecimal getRiskWidth() {
		return riskWidth;
	}

	public void setRiskWidth(BigDecimal riskWidth) {
		this.riskWidth = riskWidth;
	}

	public BigDecimal getRiskHight() {
		return riskHight;
	}

	public void setRiskHight(BigDecimal riskHight) {
		this.riskHight = riskHight;
	}
	
	
}
