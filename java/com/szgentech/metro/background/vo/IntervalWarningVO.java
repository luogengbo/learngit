package com.szgentech.metro.background.vo;


/**
 * 监测预警VO
 * @author 
 *
 */
public class IntervalWarningVO implements java.io.Serializable{
	private static final long serialVersionUID = -668426441450982473L;
	private Long intervalId; //线路区间id
	private String param;//参数 盾构字典表字段名
	private Integer category;//分类 推进预警1 拼装预警2 停机预警4
	private Integer startRingNum;//开始环号
	private Integer endRingNum;//结束环号
	private Float redWarningMax;//红色预警上限
	private Float orangeWarningMax;//橙色预警上限
	private Float orangeWarningMin;//橙色预警下限
	private Float redWarningMin;//红色预警下限
	private String leftOrRight;//左/右线标记l左r右
	private Integer lineNo;//线路编号
	private Integer intervalMark;//工程标号
	private Float yellowWarningMax; //黄色预警上限
	private Float yellowWarningMin; //黄色预警下限
	private Integer scanningFrequency; //预警扫描频率
	private Integer redDuration; //红色预警持续时间
	private Integer orangeDuration; //橙色预警持续时间
	private Integer yellowDuration; //黄色预警持续时间
	
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
	/**
	 * 获取param
	 * @return param param
	 */
	public String getParam() {
		return param;
	}
	/**
	 * 设置param
	 * @param param param 
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * 获取category
	 * @return category category
	 */
	public Integer getCategory() {
		return category;
	}
	/**
	 * 设置category
	 * @param category category
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * 获取startRingNum
	 * @return startRingNum startRingNum
	 */
	public Integer getStartRingNum() {
		return startRingNum;
	}
	/**
	 * 设置startRingNum
	 * @param startRingNum startRingNum 
	 */
	public void setStartRingNum(Integer startRingNum) {
		this.startRingNum = startRingNum;
	}
	/**
	 * 获取endRingNum
	 * @return endRingNum endRingNum
	 */
	public Integer getEndRingNum() {
		return endRingNum;
	}
	/**
	 * 设置endRingNum
	 * @param endRingNum endRingNum 
	 */
	public void setEndRingNum(Integer endRingNum) {
		this.endRingNum = endRingNum;
	}
	/**
	 * 获取redWarningMax
	 * @return redWarningMax redWarningMax
	 */
	public Float getRedWarningMax() {
		return redWarningMax;
	}
	/**
	 * 设置redWarningMax
	 * @param redWarningMax redWarningMax 
	 */
	public void setRedWarningMax(Float redWarningMax) {
		this.redWarningMax = redWarningMax;
	}
	/**
	 * 获取orangeWarningMax
	 * @return orangeWarningMax orangeWarningMax
	 */
	public Float getOrangeWarningMax() {
		return orangeWarningMax;
	}
	/**
	 * 设置orangeWarningMax
	 * @param orangeWarningMax orangeWarningMax 
	 */
	public void setOrangeWarningMax(Float orangeWarningMax) {
		this.orangeWarningMax = orangeWarningMax;
	}
	/**
	 * 获取orangeWarningMin
	 * @return orangeWarningMin orangeWarningMin
	 */
	public Float getOrangeWarningMin() {
		return orangeWarningMin;
	}
	/**
	 * 设置orangeWarningMin
	 * @param orangeWarningMin orangeWarningMin 
	 */
	public void setOrangeWarningMin(Float orangeWarningMin) {
		this.orangeWarningMin = orangeWarningMin;
	}
	/**
	 * 获取redWarningMin
	 * @return redWarningMin redWarningMin
	 */
	public Float getRedWarningMin() {
		return redWarningMin;
	}
	/**
	 * 设置redWarningMin
	 * @param redWarningMin redWarningMin 
	 */
	public void setRedWarningMin(Float redWarningMin) {
		this.redWarningMin = redWarningMin;
	}
	/**
	 * 获取leftOrRight
	 * @return leftOrRight leftOrRight
	 */
	public String getLeftOrRight() {
		return leftOrRight;
	}
	/**
	 * 设置leftOrRight
	 * @param leftOrRight leftOrRight 
	 */
	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}
	/**
	 * 获取lineNo
	 * @return lineNo lineNo
	 */
	public Integer getLineNo() {
		return lineNo;
	}
	/**
	 * 设置lineNo
	 * @param lineNo lineNo 
	 */
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	/**
	 * 获取intervalMark
	 * @return intervalMark intervalMark
	 */
	public Integer getIntervalMark() {
		return intervalMark;
	}
	/**
	 * 设置intervalMark
	 * @param intervalMark intervalMark 
	 */
	public void setIntervalMark(Integer intervalMark) {
		this.intervalMark = intervalMark;
	}
	public Float getYellowWarningMax() {
		return yellowWarningMax;
	}
	public void setYellowWarningMax(Float yellowWarningMax) {
		this.yellowWarningMax = yellowWarningMax;
	}
	public Float getYellowWarningMin() {
		return yellowWarningMin;
	}
	public void setYellowWarningMin(Float yellowWarningMin) {
		this.yellowWarningMin = yellowWarningMin;
	}
	public Integer getScanningFrequency() {
		return scanningFrequency;
	}
	public void setScanningFrequency(Integer scanningFrequency) {
		this.scanningFrequency = scanningFrequency;
	}
	public Integer getRedDuration() {
		return redDuration;
	}
	public void setRedDuration(Integer redDuration) {
		this.redDuration = redDuration;
	}
	public Integer getOrangeDuration() {
		return orangeDuration;
	}
	public void setOrangeDuration(Integer orangeDuration) {
		this.orangeDuration = orangeDuration;
	}
	public Integer getYellowDuration() {
		return yellowDuration;
	}
	public void setYellowDuration(Integer yellowDuration) {
		this.yellowDuration = yellowDuration;
	}
	
	
}
