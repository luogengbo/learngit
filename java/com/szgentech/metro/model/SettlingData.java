package com.szgentech.metro.model;

public class SettlingData implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751629275935128036L;

	private SettlingVelocity settlingVelocity; //沉降速率
	private SettlingTotal settlingTotal; //累计沉降
	
	public SettlingVelocity getSettlingVelocity() {
		return settlingVelocity;
	}
	public void setSettlingVelocity(SettlingVelocity settlingVelocity) {
		this.settlingVelocity = settlingVelocity;
	}
	public SettlingTotal getSettlingTotal() {
		return settlingTotal;
	}
	public void setSettlingTotal(SettlingTotal settlingTotal) {
		this.settlingTotal = settlingTotal;
	}
	
}
