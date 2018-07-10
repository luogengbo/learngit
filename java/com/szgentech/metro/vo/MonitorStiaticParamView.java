package com.szgentech.metro.vo;

import java.util.List;

/**
 * 参数指标
 * @author MAJL
 *
 */
public class MonitorStiaticParamView {
	private String name;//参数名称
	private String param;//参数
	private String type = "line";
	private int yAxisIndex = 0; 
	private List<Object> data;//参数值集合（按日期或环号）
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public int getyAxisIndex() {
		return yAxisIndex;
	}
	public void setyAxisIndex(int yAxisIndex) {
		this.yAxisIndex = yAxisIndex;
	}
}
