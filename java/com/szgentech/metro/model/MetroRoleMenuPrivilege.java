package com.szgentech.metro.model;

import java.util.Date;
/**
 * 角色功能权限
 * @author luohao
 *
 */
public class MetroRoleMenuPrivilege implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4680459940430286699L;
	private Long id;//系统ID
	private Long menuId;//菜单ID
	private Long privilegeId;//权限ID
	private Long roleId;//角色ID
	private Date createTime;//创建时间
	public Long getId() {
		return id;
	}
	public Long getMenuId() {
		return menuId;
	}
	public Long getPrivilegeId() {
		return privilegeId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
