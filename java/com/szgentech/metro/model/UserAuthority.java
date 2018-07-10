package com.szgentech.metro.model;

/**
 * 接收用户权限类
* @Title: UserAuthority.java 
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2018年4月3日 下午4:17:43 
* @version V1.0
 */
public class UserAuthority implements java.io.Serializable{

	private static final long serialVersionUID = 141596092999418389L;
	
	private String username;// 账号
	private String password;//密码
	private String name;//姓名
	private String phoneNo;//手机号
	private String deptIds;//部门ID
	private Long roleId;//角色ID
	private String dataRight;//数据权限
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getDataRight() {
		return dataRight;
	}
	public void setDataRight(String dataRight) {
		this.dataRight = dataRight;
	}
	
	
	
}
