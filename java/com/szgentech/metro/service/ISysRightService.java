package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.model.Homepage;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroLineIntervaliLR;
import com.szgentech.metro.model.MetroSysMenu;

/**
 * 用户权限数据查询接口
 * @author MAJL
 *
 */
public interface ISysRightService {
	/**
	 * 通过用户角色Id返回对应的权限内菜单信息
	 * @param roleId 角色Id
	 * @return
	 */
	List<MetroSysMenu> getRightMenusByRoleId(Long roleId);
	
	/**
	 * 通过用户Id返回对应的用户数据权限信息
	 * @param userId
	 * @return
	 */
	MetroCity getRightDatasByUserId(Long userId);
	
	/**
	 * 重置角色菜单权限
	 * @param roleId
	 * @return
	 */
	boolean setRightMenusByRoleId(Long roleId);
	
	/**
	 * 重置用户数据权限
	 * @param userId
	 * @return
	 */
	boolean setRightDatasByUserId(Long userId);
	
	/**
	 * APP盾构施工监控信息管理（点击查询详情）
	 * @return linename
	 * @return intervalname
	 * @return leftorright
	 */
	List<MetroLineIntervalLr> getfindLineIntervalLr(String linename,String intervalname,String leftorright);
	
	/**
	 * APP通过用户Id返回对应的用户数据权限信息（获取地铁的线路）
	 * @param userId
	 * @return
	 */
	List<MetroLine> getCircuitUserId(Long userId);
	
	/**
	 * APP通过用户Id返回对应的用户数据权限信息（获取线路左右区间）
	 * @param userId
	 * @return linename
	 */
	List<MetroLineIntervaliLR> getfindIntervaliLR(Long userId,String linename);
	
	/**
	 * APP通过用户Id返回对应的用户数据权限信息（获取地铁的线路）
	 * @param userId
	 * @return
	 */
	List<Homepage> getfindHomepage(Long userId);
}
