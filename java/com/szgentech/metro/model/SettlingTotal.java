package com.szgentech.metro.model;

import java.util.Date;

/**
 * 
* @Title: SettlingTotal.java 
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2017年12月5日 下午5:38:16 
* @version V1.0
 */
public class SettlingTotal implements java.io.Serializable {

	/**
	 * APP累计沉降
	 * 
	 * @author admin
	 */
	private static final long serialVersionUID = 4159553992274295276L;

	private Integer id;
	private String mdno;//沉降点名称
	private Float sumvar;//沉降速率预警值
	private Date monitordate; // 更新时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMdno() {
		return mdno;
	}
	public void setMdno(String mdno) {
		this.mdno = mdno;
	}
	public Float getSumvar() {
		return sumvar;
	}
	public void setSumvar(Float sumvar) {
		this.sumvar = sumvar;
	}
	public Date getMonitordate() {
		return monitordate;
	}
	public void setMonitordate(Date monitordate) {
		this.monitordate = monitordate;
	}

}
