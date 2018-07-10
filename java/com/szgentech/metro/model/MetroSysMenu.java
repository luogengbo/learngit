package com.szgentech.metro.model;

import java.util.Date;
import java.util.List;

/**
 * 系统菜单
 * @author MAJL
 *
 */
public class MetroSysMenu implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2119895681168681281L;
	private Long id;//系统ID
	private String menuName;//菜单名称
	private String menuUrl;//菜单访问URL
	private int level;//级别 1》2》3》n
//	private Long parentId;//上级菜单来源本表系统ID
	private String menuLogUrl;//菜单图标URL
	private String menuDescribe;//描述
	private int isUsed;//是否启用0否1是
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	private List<MetroSysMenu> childMenus;//子菜单集合
	
	public MetroSysMenu(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMenuLogUrl() {
		return menuLogUrl;
	}

	public void setMenuLogUrl(String menuLogUrl) {
		this.menuLogUrl = menuLogUrl;
	}

	public String getMenuDescribe() {
		return menuDescribe;
	}

	public void setMenuDescribe(String menuDescribe) {
		this.menuDescribe = menuDescribe;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
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

	public List<MetroSysMenu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<MetroSysMenu> childMenus) {
		this.childMenus = childMenus;
	}
	
	
}
