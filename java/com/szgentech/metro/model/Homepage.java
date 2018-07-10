package com.szgentech.metro.model;

import java.util.List;

public class Homepage implements java.io.Serializable{

	/**
	 * 查询主页信息（单独查询线路的，关联的区间左右线）
	 * 
	 */
	private static final long serialVersionUID = 286088412974778934L;

	private Long id;
	private String linename; //线路
	private List<MetroLineIntervaliLR> intervaliLR;
	
	public List<MetroLineIntervaliLR> getIntervaliLR() {
		return intervaliLR;
	}
	public void setIntervaliLR(List<MetroLineIntervaliLR> intervaliLR) {
		this.intervaliLR = intervaliLR;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	
}
