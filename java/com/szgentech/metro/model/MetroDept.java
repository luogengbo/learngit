package com.szgentech.metro.model;

import java.util.Date;
import java.util.List;

/**
 * 部门
 * @author MAJL
 *
 */
public class MetroDept implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8903616571131818638L;
	
	
	public MetroDept(){}
	
	private Long id;//系统ID
	private String deptNo;//部门编号
	private String deptName;//部门名称
	private int isDel;//是否删除0否 1是
	private Date updateTime;//更新时间
	
	public MetroDept(String deptName){
		this.deptName = deptName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
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

	
}
