package com.szgentech.metro.model;
/**
 * 预警统计
 * @author luohao
 *
 */
public class DaiyWarnStatistics implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dicMean;//参数含义 盾构字典表
	
	private String paramName;//参数名称 盾构字典表字段名
	
	private int singleSum;// 单个参数预警总次数
	
	private int confirmSum;//已确认的预警次数
	
	private int pushSum;//已推送预警短信（响应）数量

	public String getDicMean() {
		return dicMean;
	}

	public String getParamName() {
		return paramName;
	}

	public int getSingleSum() {
		return singleSum;
	}

	public int getConfirmSum() {
		return confirmSum;
	}

	public int getPushSum() {
		return pushSum;
	}

	public void setDicMean(String dicMean) {
		this.dicMean = dicMean;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setSingleSum(int singleSum) {
		this.singleSum = singleSum;
	}

	public void setConfirmSum(int confirmSum) {
		this.confirmSum = confirmSum;
	}

	public void setPushSum(int pushSum) {
		this.pushSum = pushSum;
	}

	

	
}
