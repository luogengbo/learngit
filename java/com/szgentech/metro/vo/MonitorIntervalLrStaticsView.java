package com.szgentech.metro.vo;

import java.util.List;

public class MonitorIntervalLrStaticsView {
	private List<String> pNames;//指标名称数组
	private List<String> keys;//环号数组或日期数组
	private List<MonitorStiaticParamView> values;//参数结果

	public List<String> getpNames() {
		return pNames;
	}
	public void setpNames(List<String> pNames) {
		this.pNames = pNames;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	public List<MonitorStiaticParamView> getValues() {
		return values;
	}
	public void setValues(List<MonitorStiaticParamView> values) {
		this.values = values;
	}
	
	
}
