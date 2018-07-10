package com.szgentech.metro.model;

import java.util.Date;

/**
 * 用户操作记录
 * @author MAJL
 *
 */
public class MetroUserOperateRec implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5054335409060989249L;
	private Long id;//系统ID
	private Long userId;//用户ID来源于用户表系统ID
	private String username;//用户名来源于用户表用户名
	private String visitMenu;//访问模块描述当前操作所在菜单模块
	private String operation;//具体操作记录当前操作类型
	private String loginIp;//访问IP来源于用户表登陆IP
	private Date visitTime;//访问时间用户具体操作时间
	private Date createTime;//创建时间
	
	public MetroUserOperateRec(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVisitMenu() {
		return visitMenu;
	}

	public void setVisitMenu(String visitMenu) {
		this.visitMenu = visitMenu;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
