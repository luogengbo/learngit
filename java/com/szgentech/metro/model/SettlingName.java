package com.szgentech.metro.model;

public class SettlingName implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1068170543843492142L;

	private Long id;
	private String spname; //沉降点名称
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSpname() {
		return spname;
	}
	public void setSpname(String spname) {
		this.spname = spname;
	}
	
}
