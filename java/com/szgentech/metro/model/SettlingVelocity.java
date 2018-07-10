package com.szgentech.metro.model;

import java.util.Date;

/**
 * @Title: SettlingVelocity.java
 * @Package com.szgentech.metro.model
 * @author hjf
 * @date 2017年12月5日 下午5:38:45
 * @version V1.0
 */
public class SettlingVelocity implements java.io.Serializable {

	private static final long serialVersionUID = 8917989258499292528L;

	private Integer id;
	private String mdno;// 沉降点名称
	private Float thisvar;// 沉降速率预警值
	private Date monitordate; // 更新时间

	public SettlingVelocity() {
	}

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

	public Float getThisvar() {
		return thisvar;
	}

	public void setThisvar(Float thisvar) {
		this.thisvar = thisvar;
	}

	public Date getMonitordate() {
		return monitordate;
	}

	public void setMonitordate(Date monitordate) {
		this.monitordate = monitordate;
	}

}
