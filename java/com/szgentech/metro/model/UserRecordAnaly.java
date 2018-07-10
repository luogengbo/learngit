package com.szgentech.metro.model;

import java.util.List;

/**
 * 
* @Title: 用户操作记录分析实体类（某个模块操作次数）
* @Package com.szgentech.metro.model 
* @author hjf
* @date 2018年4月20日 上午11:35:27 
* @version V1.0
 */
public class UserRecordAnaly implements java.io.Serializable{

	private static final long serialVersionUID = -6481089515354024046L;
	
	private String name;//操作人名字
	private String visitMenu;//访问模块描述当前操作所在菜单模块
	private int amount;//此模块操作次数 
	private List<Operation> operations;
	
	public List<Operation> getOperations() {
		return operations;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVisitMenu() {
		return visitMenu;
	}
	public void setVisitMenu(String visitMenu) {
		this.visitMenu = visitMenu;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
