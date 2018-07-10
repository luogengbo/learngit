package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.model.Homepage;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroLineIntervaliLR;
import com.szgentech.metro.model.MetroRole;
import com.szgentech.metro.model.MetroUser;

public interface ISysRightDao {
	/**
	 * 查找角色权限菜单
	 * @param params
	 * @return
	 */
	List<MetroRole> findRoleRightMenus(Map<String, Object> params);
	
	/**
	 * 查找用户权限数据
	 * @param params
	 * @return
	 */
	List<MetroUser> findUserRightDatas(Map<String, Object> params);
	
	/**
	 * APP盾构施工监控信息管理（点击查询详情）
	 * @param params
	 */
	List<MetroLineIntervalLr> findLineIntervalLr(Map<String, Object> params);
	
	/**
	 * APP查询线路（线路线路选择）
	 * @param params
	 */
	List<MetroLine> findCircuitUserId(Map<String, Object> params);
	
	/**
	 * APP查询线路（区间左右线）
	 * @param params
	 */
	List<MetroLineIntervaliLR> findIntervaliLR(Map<String, Object> params);
	
	/**
	 * APP查询线路（一次查询返回所有数据）
	 * @param params
	 */
	List<Homepage> findHomepage(Map<String, Object> params);
}
