package com.szgentech.metro.model;

import java.util.Date;

/**
 * APP版本更新
* @Title: MetroUpdateDaTa.java 
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2018年1月31日 上午9:43:03 
* @version V1.0
 */
public class MetroUpdateApp implements java.io.Serializable{

	private static final long serialVersionUID = -1452386687332099797L;
	private Long upId;//ID
	private String upAppname; //APP名字
	private int upversioncode;//版本号
	private String upversionname;//版本名称
	private String upUpdateURL;//APP的URL地址
	private String upUpdatingContent;//更新内容
	private Integer facilityflag;//设备类型:1、Android  2、Los
	private Integer upWhetherupdating;//是否强制更新（不更新不能使用）1是2不是
	private Date createTime;//更新时间
	
	private int code;

	public Long getUpId() {
		return upId;
	}

	public void setUpId(Long upId) {
		this.upId = upId;
	}

	public String getUpAppname() {
		return upAppname;
	}

	public void setUpAppname(String upAppname) {
		this.upAppname = upAppname;
	}

	public int getUpversioncode() {
		return upversioncode;
	}

	public void setUpversioncode(int upversioncode) {
		this.upversioncode = upversioncode;
	}

	public String getUpversionname() {
		return upversionname;
	}

	public void setUpversionname(String upversionname) {
		this.upversionname = upversionname;
	}

	public String getUpUpdateURL() {
		return upUpdateURL;
	}

	public void setUpUpdateURL(String upUpdateURL) {
		this.upUpdateURL = upUpdateURL;
	}

	public String getUpUpdatingContent() {
		return upUpdatingContent;
	}

	public void setUpUpdatingContent(String upUpdatingContent) {
		this.upUpdatingContent = upUpdatingContent;
	}

	public Integer getFacilityflag() {
		return facilityflag;
	}

	public void setFacilityflag(Integer facilityflag) {
		this.facilityflag = facilityflag;
	}

	public Integer getUpWhetherupdating() {
		return upWhetherupdating;
	}

	public void setUpWhetherupdating(Integer upWhetherupdating) {
		this.upWhetherupdating = upWhetherupdating;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
