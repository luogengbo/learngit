package com.szgentech.metro.model;

import java.util.Date;
import java.util.List;

public class Pandect implements java.io.Serializable {

	/**
	 * APP总览数据
	 */
	private static final long serialVersionUID = -2711429633962344059L;

	private Float A0001; // 当前环数
	private Integer A0002; // 工作状态
	private Integer today;// 今日完成
	private Integer total; // 累计完成
	private Float totalRingNum; // 总环数
	private Date updateTime;// 时间
	private int rednessUpper;// 红色上限
	private int rednessMinimum;// 红色下限
	private int orangeUpper;// 橙色上限
	private int orangeMinimuml;// 橙色下限
	private List<Object> ringNum;// 推进环数 
	
	private int yellowWarningMinNum;// 黄色下限
    private int yellowWarningMaxNum;// 黄色上限
    private String currentRiskMsg;//当前风险点信息
    private String nearRiskMsg;//临近风险点信息
    private int weekTotalSmsWarn; //本周短信发出预警次数
    
    
	
	
	
	public int getYellowWarningMinNum() {
		return yellowWarningMinNum;
	}
	public void setYellowWarningMinNum(int yellowWarningMinNum) {
		this.yellowWarningMinNum = yellowWarningMinNum;
	}
	public int getYellowWarningMaxNum() {
		return yellowWarningMaxNum;
	}
	public void setYellowWarningMaxNum(int yellowWarningMaxNum) {
		this.yellowWarningMaxNum = yellowWarningMaxNum;
	}
	public String getCurrentRiskMsg() {
		return currentRiskMsg;
	}
	public void setCurrentRiskMsg(String currentRiskMsg) {
		this.currentRiskMsg = currentRiskMsg;
	}
	public String getNearRiskMsg() {
		return nearRiskMsg;
	}
	public void setNearRiskMsg(String nearRiskMsg) {
		this.nearRiskMsg = nearRiskMsg;
	}
	public int getWeekTotalSmsWarn() {
		return weekTotalSmsWarn;
	}
	public void setWeekTotalSmsWarn(int weekTotalSmsWarn) {
		this.weekTotalSmsWarn = weekTotalSmsWarn;
	}
	public Float getA0001() {
		return A0001;
	}
	public void setA0001(Float a0001) {
		A0001 = a0001;
	}
	public Integer getA0002() {
		return A0002;
	}
	public void setA0002(Integer a0002) {
		A0002 = a0002;
	}
	public Integer getToday() {
		return today;
	}
	public void setToday(Integer today) {
		this.today = today;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Float getTotalRingNum() {
		return totalRingNum;
	}
	public void setTotalRingNum(Float totalRingNum) {
		this.totalRingNum = totalRingNum;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getRednessUpper() {
		return rednessUpper;
	}
	public void setRednessUpper(int rednessUpper) {
		this.rednessUpper = rednessUpper;
	}
	public int getRednessMinimum() {
		return rednessMinimum;
	}
	public void setRednessMinimum(int rednessMinimum) {
		this.rednessMinimum = rednessMinimum;
	}
	public int getOrangeUpper() {
		return orangeUpper;
	}
	public void setOrangeUpper(int orangeUpper) {
		this.orangeUpper = orangeUpper;
	}
	public int getOrangeMinimuml() {
		return orangeMinimuml;
	}
	public void setOrangeMinimuml(int orangeMinimuml) {
		this.orangeMinimuml = orangeMinimuml;
	}
	public List<Object> getRingNum() {
		return ringNum;
	}
	public void setRingNum(List<Object> ringNum) {
		this.ringNum = ringNum;
	}

}
