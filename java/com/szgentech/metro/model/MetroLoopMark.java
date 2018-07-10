package com.szgentech.metro.model;

/**
 * 
* @Title: MetroLoopMark.java 
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2018年1月17日 下午5:05:27 
* @version V1.0
 */
public class MetroLoopMark implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Float A0001; // 当前环数
	private int machineType;//盾构机类型
	private int communiStatus; // 通信状态
	
	public int getMachineType() {
		return machineType;
	}
	public void setMachineType(int machineType) {
		this.machineType = machineType;
	}
	public Float getA0001() {
		return A0001;
	}
	public void setA0001(Float a0001) {
		A0001 = a0001;
	}
	public int getCommuniStatus() {
		return communiStatus;
	}
	public void setCommuniStatus(int communiStatus) {
		this.communiStatus = communiStatus;
	}
	
}
