package com.szgentech.metro.vo;

import java.util.Date;

public class SessionUser implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1742508134624155250L;
	private Long id;//系统ID
	private Long roleId;//角色ID
	private String username;//账号
	private String name;//姓名
	private	Integer sex;//性别1男0女
	private Date loginTime;//登陆时间
	private String loginIp;//登陆IP
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	} 
}
