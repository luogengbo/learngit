package com.szgentech.metro.model;

import java.util.List;

public class SettlingParticulars implements java.io.Serializable{

	/**
	 * 沉降监测详情
	 */
	private static final long serialVersionUID = 4416523553540956849L;

	private List<SettlingSp> settlingSps;//沉降速率详情
	private List<SettlingMd> settlingMds;//累计沉降详情

	public List<SettlingSp> getSettlingSps() {
		return settlingSps;
	}
	public void setSettlingSps(List<SettlingSp> settlingSps) {
		this.settlingSps = settlingSps;
	}
	public List<SettlingMd> getSettlingMds() {
		return settlingMds;
	}
	public void setSettlingMds(List<SettlingMd> settlingMds) {
		this.settlingMds = settlingMds;
	}
	
}
