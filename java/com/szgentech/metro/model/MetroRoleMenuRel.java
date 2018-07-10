package com.szgentech.metro.model;

import java.util.Date;

/**
 * 角色菜单权限
 * @author MAJL
 *
 */
public class MetroRoleMenuRel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1533185386295008473L;
	private Long id;//系统ID
	private Long roleId;//角色ID
	private Long menuId;//菜单ID
	private Date createTime;//创建时间
	
	public MetroRoleMenuRel(){}

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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
