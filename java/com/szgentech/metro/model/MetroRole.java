package com.szgentech.metro.model;

import java.util.Date;
import java.util.List;

/**
 * 角色
 * @author MAJL
 *
 */
public class MetroRole implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3225441789687807601L;
	private Long id;//系统ID
	private String roleCode;//角色代码
	private String roleName;//角色名称
	private int isUsed;//是否启用0否1是
	private String roleDescribe;//描述
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	private List<MetroUser> userList;//用户集合
	private List<MetroRoleMenuRel> menuRightList;//菜单权限集合
	private List<MetroSysMenu> menus;//权限菜单
	
	public MetroRole(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public String getRoleDescribe() {
		return roleDescribe;
	}

	public void setRoleDescribe(String roleDescribe) {
		this.roleDescribe = roleDescribe;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<MetroUser> getUserList() {
		return userList;
	}

	public void setUserList(List<MetroUser> userList) {
		this.userList = userList;
	}

	public List<MetroRoleMenuRel> getMenuRightList() {
		return menuRightList;
	}

	public void setMenuRightList(List<MetroRoleMenuRel> menuRightList) {
		this.menuRightList = menuRightList;
	}

	public List<MetroSysMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<MetroSysMenu> menus) {
		this.menus = menus;
	}
	
	
	
}
