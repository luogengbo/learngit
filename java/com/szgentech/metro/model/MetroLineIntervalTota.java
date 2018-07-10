package com.szgentech.metro.model;

import java.util.List;

/**
 * 
* @Title: 预警记录分析统计
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2018年4月26日 上午11:54:05 
* @version V1.0
 */
public class MetroLineIntervalTota implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private int rednessUpper;// 红色上限
	private int rednessMinimum;// 红色下限
	private int orangeUpper;// 橙色上限
	private int orangeMinimuml;// 橙色下限
	private int yellowWarningMinNum;// 黄色下限
    private int yellowWarningMaxNum;// 黄色上限
    private int confirmLevelTota;//人工确认次数
	private int isPushTota;//推送次数
	
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
	public int getConfirmLevelTota() {
		return confirmLevelTota;
	}
	public void setConfirmLevelTota(int confirmLevelTota) {
		this.confirmLevelTota = confirmLevelTota;
	}
	public int getIsPushTota() {
		return isPushTota;
	}
	public void setIsPushTota(int isPushTota) {
		this.isPushTota = isPushTota;
	}
    
}
