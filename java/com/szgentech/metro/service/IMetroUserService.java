package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroUser;

/**
 * 用户业务接口
 * 
 * @author MAJL
 *
 */
public interface IMetroUserService {
	/**
	 * 分页查询
	 * 
	 * @param deptId
	 *            部门Id
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	PageResultSet<MetroUser> findMetroUserInfo(String deptId, int pageNum, int pageSize);

	/**
	 * 通过名称查找用户
	 * 
	 * @param name
	 *            用户名
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	PageResultSet<MetroUser> findMetroUserInfoByName(String name, int pageNum, int pageSize);

	/**
	 * 添加用户
	 * 
	 * @param username
	 *            用户账号
	 * @param password
	 *            用户密码
	 * @param name
	 *            用户姓名
	 * @param deptIds
	 *            所属部门Id多个Id用逗号分隔
	 * @param roleId
	 *            角色Id
	 * @param phoneNo
	 *            联系电话
	 * @param founder
	 *            创建人
	 * @return
	 */
	boolean addMetroUserInfo(String username, String password, String name, String deptIds, Long roleId, String phoneNo, String founder);
 
	/**
	 * 添加用户
	 * 
	 * @param username
	 *            用户账号
	 * @param password
	 *            用户密码
	 * @param name
	 *            用户姓名
	 * @param deptIds
	 *            所属部门Id多个Id用逗号分隔
	 * @param roleId
	 *            角色Id
	 * @param phoneNo
	 *            联系电话
	 * @param dataRigh
	 *            数据权限
	 * @return
	 */
	boolean addMetroUserInfo2(String username, String password, String name, String deptIds, Long roleId, String phoneNo,String dataRigh);
	
	/**
	 * 编辑用户信息
	 * 
	 * @param userId
	 *            用户Id
	 * @param username
	 *            用户账号
	 * @param name
	 *            用户姓名
	 * @param oldDeptIds
	 *            修改之前的所属部门Id多个Id用逗号分隔
	 * @param deptIds
	 *            所属部门Id多个Id用逗号分隔
	 * @param roleId
	 *            角色Id
	 * @param phoneNo 
	 *            联系电话
	 * @return
	 */
	boolean editMetroUserInfo(String userId, String username, String name, String oldDeptIds, String deptIds, Long roleId, String phoneNo);

	/**
	 * 删除用户信息
	 * 
	 * @param deptId
	 *            部门Id
	 * @param userId
	 *            用户Id
	 * @param deptSize
	 *            所属部门Id个数
	 * @return
	 */
	boolean delMetroUserInfo(String deptId, String userId, int deptSize);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户Id
	 * @param password
	 *            新密码
	 * @return
	 */
	boolean editMetroUserPassword(String userId, String password);

	/**
	 * 添加用户到部门
	 * 
	 * @param deptId
	 *            部门Id
	 * @param userIds
	 *            用户Id多个Id用逗号分隔
	 * @return
	 */
	boolean addMetroUserToDept(String deptId, String userIds);

	/**
	 * 检查账号是否已存在
	 * 
	 * @param username
	 * @return
	 */
	boolean findMetroUserUsername(String username);

	/**
	 * 查找所有用户
	 * 
	 * @return
	 */
	List<MetroUser> findAllUser();

	/**
	 * 查找用户实体
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	PageResultSet<MetroUser> findAllUser(String name, int pageNum, int pageSize);

	/**
	 * 通过id查询用户信息
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 */
	MetroUser findObjById(Long userId);
	
	/**
	 * 根据部门的ID取部门下所有有效用户信息
	 * 
	 * @param deptId
	 *            部门ID
	 * @return
	 */
	public List<MetroUser> findAllUserByDeptId(Long deptId);

}
