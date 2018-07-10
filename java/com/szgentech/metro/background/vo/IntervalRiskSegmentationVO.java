package com.szgentech.metro.background.vo;

/**
 * 地质风险划分阻段划分预警VO
 * @author luohao
 *
 */
public class IntervalRiskSegmentationVO implements java.io.Serializable{

	private static final long serialVersionUID =6927875150192925666L;
	private Long id; //id
	private Long lineId;//线路ID
	private Integer lineNo;//线路编号
	private String lineName;//工程名称
	private Long intervalId; //线路区间id
	private String intervalName; //线路区间名称
	private String leftOrRight;//左/右线标记 l左r右
	private Integer intervalMark;//工程标号
	private String riskNo;//风险编号
	private String riskPoint;//风险点
	private Integer riskStartRing;//风险开始环
	private Integer riskEndRing;//风险结束环
	private Integer earlyWarningRing;//提前预警环数
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public Integer getLineNo() {
		return lineNo;
	}
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Long getIntervalId() {
		return intervalId;
	}
	public void setIntervalId(Long intervalId) {
		this.intervalId = intervalId;
	}
	public String getIntervalName() {
		return intervalName;
	}
	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}
	public String getLeftOrRight() {
		return leftOrRight;
	}
	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}
	public Integer getIntervalMark() {
		return intervalMark;
	}
	public void setIntervalMark(Integer intervalMark) {
		this.intervalMark = intervalMark;
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
	public Integer getEarlyWarningRing() {
		return earlyWarningRing;
	}
	public void setEarlyWarningRing(Integer earlyWarningRing) {
		this.earlyWarningRing = earlyWarningRing;
	}
}
