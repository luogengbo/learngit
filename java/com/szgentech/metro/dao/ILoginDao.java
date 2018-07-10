package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.model.MetroUser;

/**
 * 登录数据处理接口
 * @author MAJL
 *
 */
public interface ILoginDao {
	/**
	 * 通过用户名密码查找用户
	 * @param params
	 * @return
	 */
	MetroUser findUserByNameAndPass(Map<String, Object> params);
	
	/**
	 * 更新用户在线状态
	 * @param params
	 * @return
	 */
	int updateUserOnlineStatus(Map<String, Object> params);
}
