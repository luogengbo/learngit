package com.szgentech.metro.model;

import java.util.Date;

public class SettlingSp implements java.io.Serializable {

	/**
	 * 沉降速率详情
	 */
	private static final long serialVersionUID = 8856978575444739819L;

	private Float spsumadd; // 累计沉降正值
	private Float spsumsub; // 累计沉降负值
	private Date updatetime; // 更新时间

	public Float getSpsumadd() {
		return spsumadd;
	}

	public void setSpsumadd(Float spsumadd) {
		this.spsumadd = spsumadd;
	}

	public Float getSpsumsub() {
		return spsumsub;
	}

	public void setSpsumsub(Float spsumsub) {
		this.spsumsub = spsumsub;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
