package com.szgentech.metro.service;

import com.szgentech.metro.model.MetroUser;

/**
 * 登录业务处理接口
 * @author MAJL
 *
 */
public interface ILoginService {
	/**
	 * 检查用户名和密码是否存在
	 * @param username 用户账号或姓名
	 * @param pass 加密后的密码
	 * @return 如果存在返回MetroUser 如果不存在返回null
	 */
	MetroUser checkUserPass(String username, String pass);
	
	/**
	 * 更新用户在线状态
	 * @param userId 用户Id
	 * @param loginIp 登录Ip
	 * @param status 在线状态 1在线 0离线
	 * @return
	 */
	boolean updateUserOnlineStatus(Long userId, String loginIp, int status);
}
