package com.szgentech.metro.model;

import java.util.Date;
/**
 * 权限
 * @author luohao
 *
 */
public class MetroPrivilege implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4680459940430288999L;
	private Long id;//系统ID
	private String privilegeType;//类型
	private String privilegeDec;//备注
	private Date createTime;//创建时间
	public Long getId() {
		return id;
	}
	public String getPrivilegeType() {
		return privilegeType;
	}
	public String getPrivilegeDec() {
		return privilegeDec;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}
	public void setPrivilegeDec(String privilegeDec) {
		this.privilegeDec = privilegeDec;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
