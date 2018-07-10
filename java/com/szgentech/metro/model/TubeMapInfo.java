package com.szgentech.metro.model;

public class TubeMapInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3575068117025101317L;

	private Integer lineNo;//线路编号
	private Integer intervalMark;//区间标号
	private Integer buildStatus;//工程状态
	private String leftOrRight;//左右线标记
	private Float numValue;//预警数值
	private Integer warningLevel;//预警级别
	private String dicMean;//预警参数中文名
	private String dicUnit;//参数值单位
	
	public Integer getLineNo() {
		return lineNo;
	}
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	public Integer getIntervalMark() {
		return intervalMark;
	}
	public void setIntervalMark(Integer intervalMark) {
		this.intervalMark = intervalMark;
	}
	public Integer getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(Integer buildStatus) {
		this.buildStatus = buildStatus;
	}
	public String getLeftOrRight() {
		return leftOrRight;
	}
	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}
	public Float getNumValue() {
		return numValue;
	}
	public void setNumValue(Float numValue) {
		this.numValue = numValue;
	}
	public Integer getWarningLevel() {
		return warningLevel;
	}
	public void setWarningLevel(Integer warningLevel) {
		this.warningLevel = warningLevel;
	}
	public String getDicMean() {
		return dicMean;
	}
	public void setDicMean(String dicMean) {
		this.dicMean = dicMean;
	}
	public String getDicUnit() {
		return dicUnit;
	}
	public void setDicUnit(String dicUnit) {
		this.dicUnit = dicUnit;
	}
	
}
