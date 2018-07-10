package com.szgentech.metro.model;

import java.util.Date;

/**
 * 盾构字典
 * @author MAJL
 *
 */
public class MetroDictionary implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3352066755811022477L;
	private Long id;//系统ID
	private String dicNo;//序号
	private String dicName;//字段名
	private String dicMean;//含义
	private String dicPrecision;//精度说明:NONE-不做缩放处理;I0:需放大10倍;I00-需放大100倍;0_1-需缩小10倍;	0_01：需缩小100倍	
	private String dicUnit;//单位
	private String dicType;//类型
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	
	public MetroDictionary(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDicNo() {
		return dicNo;
	}

	public void setDicNo(String dicNo) {
		this.dicNo = dicNo;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicMean() {
		return dicMean;
	}

	public void setDicMean(String dicMean) {
		this.dicMean = dicMean;
	}

	public String getDicPrecision() {
		return dicPrecision;
	}

	public void setDicPrecision(String dicPrecision) {
		this.dicPrecision = dicPrecision;
	}

	public String getDicUnit() {
		return dicUnit;
	}

	public void setDicUnit(String dicUnit) {
		this.dicUnit = dicUnit;
	}

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
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
