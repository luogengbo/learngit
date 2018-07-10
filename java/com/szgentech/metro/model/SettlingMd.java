package com.szgentech.metro.model;

import java.util.Date;

public class SettlingMd implements java.io.Serializable {

	/**
	 * 累计沉降详情
	 */
	private static final long serialVersionUID = -2337958291437488840L;

	private Float thisvar; // 本次变化量
	private Float sumvar; // 累计变化量
	private Date updatetime;// 更新时间

	public Float getThisvar() {
		return thisvar;
	}

	public void setThisvar(Float thisvar) {
		this.thisvar = thisvar;
	}

	public Float getSumvar() {
		return sumvar;
	}

	public void setSumvar(Float sumvar) {
		this.sumvar = sumvar;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
