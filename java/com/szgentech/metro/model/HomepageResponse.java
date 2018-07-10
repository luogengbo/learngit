package com.szgentech.metro.model;

import java.util.List;

public class HomepageResponse implements java.io.Serializable{

	/**
	 * APP查询线路主页的返回
	 */
	private static final long serialVersionUID = -1528119449551419458L;

	private List<Homepage> homepages;
	private int code;
	
	public List<Homepage> getHomepages() {
		return homepages;
	}
	public void setHomepages(List<Homepage> homepages) {
		this.homepages = homepages;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
