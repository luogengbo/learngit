package com.szgentech.metro.model;

import java.util.Date;

/**
 * 系统图片
 * @author MAJL
 *
 */
public class MetroPhoto implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3384993986085214404L;
	
	private Long id;//系统ID
	private String photoName;//图片名称
	private String photoUrl;//图片访问地址
	private Integer photoType;//图片类型 1刀盘背景 2螺旋背景
	private Date updateTime;
	private Date createTime;
	
	public MetroPhoto(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getPhotoType() {
		return photoType;
	}

	public void setPhotoType(Integer photoType) {
		this.photoType = photoType;
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
