package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroUser;

/**
 * 用户管理数据处理接口
 * @author MAJL
 *
 */
public interface IMetroUserDao extends BaseDao<MetroUser> {
	/**
	 * 删除用户部门关系
	 * @param params
	 * @return
	 */
	int delUserDeptRel(Map<String, Object> params);
	
	/**
	 * 增加用户部门关系
	 * @param params
	 * @return
	 */
	int addUserDeptRel(Map<String, Object> params);
	
	/**
	 * 删除指定用户部门关系
	 * @param params
	 * @return
	 */
	int deleteObjDeptRel(Map<String, Object> params);
	
	/**
	 * 修改密码
	 * @param params
	 * @return
	 */
	int editUserPassword(Map<String, Object> params);
	
	/**
	 * 查找该账号是否存在
	 * @return
	 */
	int findMetroUserUsername(Map<String, Object> params);
	
	/**
	 * 统计用户实体
	 * @param params
	 * @return
	 */
	int countObjsr(Map<String, Object> params);
	
	/**
	 * 分页查询用户实体
	 * @param params
	 * @return
	 */
	List<MetroUser> findObjsListr(Map<String, Object> params);
	
	/**
	 * 根据部门ID查询所有用户信息
	 * @param params
	 * @return
	 */
	List<MetroUser> findAllUserByDeptId(Map<String, Object> params);

}
