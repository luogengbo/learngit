package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.model.MetroUserDataRel;

/**
 * 用户数据权限管理业务接口
 * @author MAJL
 *
 */
public interface IMetroDataRightService {
	/**
	 * 查找指定用户数据权限
	 * @param userId
	 * @return
	 */
	List<MetroUserDataRel> findUserDataRightByUserId(Long userId);
	
	/**
	 * 保存用户数据权限信息
	 * @param userId
	 * @param dataRight
	 * @return
	 */
	boolean saveDataRightInfo(Long userId, String dataRight);
	
}
