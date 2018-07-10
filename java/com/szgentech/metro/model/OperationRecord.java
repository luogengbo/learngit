package com.szgentech.metro.model;

import java.util.List;

/**
 * 
* @Title: 用户操作记录分析实体类（操作人总共操作次数）
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2018年4月19日 下午3:09:00 
* @version V1.0
 */
public class OperationRecord implements java.io.Serializable{

	private static final long serialVersionUID = 3060650806342609596L;

	private String nsername;//操作用户
	private int useramount;//总记录
	private List<UserRecordAnaly> analies;
	
	public List<UserRecordAnaly> getAnalies() {
		return analies;
	}
	public void setAnalies(List<UserRecordAnaly> analies) {
		this.analies = analies;
	}
	public String getNsername() {
		return nsername;
	}
	public void setNsername(String nsername) {
		this.nsername = nsername;
	}
	public int getUseramount() {
		return useramount;
	}
	public void setUseramount(int useramount) {
		this.useramount = useramount;
	}
	
}
