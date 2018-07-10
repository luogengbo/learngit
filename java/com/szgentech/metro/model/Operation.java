package com.szgentech.metro.model;
/**
 * 
* @Title: 用户操作记录分析实体类（操作人总共操作次数）
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2018年4月19日 下午3:09:00 
* @version V1.0
 */
public class Operation implements java.io.Serializable{
	
	private static final long serialVersionUID = 8149128521781381388L;
	
	private String operation;//具体操作记录当前操作类型
	private int moduleamount;//操作次数
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public int getModuleamount() {
		return moduleamount;
	}
	public void setModuleamount(int moduleamount) {
		this.moduleamount = moduleamount;
	}
	
}
