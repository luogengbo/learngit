package com.szgentech.metro.model;

import java.util.Date;
import java.util.List;

/**
 * 用户
 * @author MAJL
 *
 */
public class MetroUser implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6777957478732414603L;
	private Long id;//系统ID
	private Long roleId;//角色ID
	private String username;//账号
	private String password;//密码
	private String name;//姓名
	private	Integer sex;//性别1男0女
	private Date loginTime;//登陆时间
	private String loginIp;//登陆IP
	private Integer onlineStatus;//在线状态1在线0不在线 通过最后操作时间与当前时间对比超出30分钟下线
	private Integer isUsed; //用户状态 0禁用 1启用
	private Date updateTime;//修改时间
	private Date createTime;//创建时间
	private String phoneNo; //联系电话
	private String founder; //创建人
	private MetroRole role;//角色
	private List<MetroUserDataRel> udrList;//用户数据权限集合
	private List<MetroDept> deptList;//部门集合
	private MetroCity city;//城市
	
	public MetroUser(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public List<MetroUserDataRel> getUdrList() {
		return udrList;
	}

	public void setUdrList(List<MetroUserDataRel> udrList) {
		this.udrList = udrList;
	}

	public List<MetroDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<MetroDept> deptList) {
		this.deptList = deptList;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取isUsed
	 * @return isUsed isUsed
	 */
	public Integer getIsUsed() {
		return isUsed;
	}

	/**
	 * 设置isUsed
	 * @param isUsed isUsed 
	 */
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public MetroRole getRole() {
		return role;
	}

	public void setRole(MetroRole role) {
		this.role = role;
	}

	public MetroCity getCity() {
		return city;
	}

	public void setCity(MetroCity city) {
		this.city = city;
	}

}
