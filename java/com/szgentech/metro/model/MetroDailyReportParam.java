package com.szgentech.metro.model;

/**
 * 风险分析日报表截图post
 * @author luohao
 *
 */
public class MetroDailyReportParam extends MetroDailyReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1859422776135915554L;

	private int type; 
	
	private int goNumA = 0;
	
	private String pingLorR = "";
	
	private String saveOrUpdate;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGoNumA() {
		return goNumA;
	}

	public void setGoNumA(int goNumA) {
		this.goNumA = goNumA;
	}

	public String getPingLorR() {
		return pingLorR;
	}

	public void setPingLorR(String pingLorR) {
		this.pingLorR = pingLorR;
	}

	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}

	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}
	
	
}
