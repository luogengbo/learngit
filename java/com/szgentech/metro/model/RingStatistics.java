package com.szgentech.metro.model;
/**
 * 环报表统计
 * @author luohao
 *
 */
public class RingStatistics implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String paramName;//参数名称 盾构字典表字段名
	
	private Float avgValue;// 平均值
	
	private Float maxValue;//最大值
	
	private Float minValue;//最小值
	
	private Float sumValue;// 总和

	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Float getAvgValue() {
		return avgValue;
	}

	public void setAvgValue(Float avgValue) {
		this.avgValue = avgValue;
	}

	public Float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Float maxValue) {
		this.maxValue = maxValue;
	}

	public Float getMinValue() {
		return minValue;
	}

	public void setMinValue(Float minValue) {
		this.minValue = minValue;
	}

	public Float getSumValue() {
		return sumValue;
	}

	public void setSumValue(Float sumValue) {
		this.sumValue = sumValue;
	}
	
}
