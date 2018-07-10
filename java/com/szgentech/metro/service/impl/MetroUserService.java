package com.szgentech.metro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.Page;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroDataRightDao;
import com.szgentech.metro.dao.IMetroUserDao;
import com.szgentech.metro.model.MetroUser;
import com.szgentech.metro.model.MetroUserDataRel;
import com.szgentech.metro.service.IMetroUserService;

@Service("userService")
public class MetroUserService extends BaseService<MetroUser> implements IMetroUserService {
	@Autowired
	private IMetroUserDao userDao;
	@Autowired
	private IMetroDataRightDao drDao;

	@Override
	public PageResultSet<MetroUser> findMetroUserInfo(String deptId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		return getPageResultSet(params, pageNum, pageSize, userDao);
	}

	@Override
	public PageResultSet<MetroUser> findMetroUserInfoByName(String name, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		return getPageResultSet(params, pageNum, pageSize, userDao);
	}

	@Override
	public boolean addMetroUserInfo(String username, String password, String name, String deptIds, Long roleId, String phoneNo, String founder) {
		MetroUser user = new MetroUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setRoleId(roleId);
		user.setPhoneNo(phoneNo);
		user.setFounder(founder);
		int count = userDao.insertObj(user);
		Map<String, Object> params = new HashMap<>();
		params.put("userId", user.getId());
		params.put("deptIds", deptIds.split(","));
		count = count + userDao.addUserDeptRel(params);
		return count > 1 ? true : false;
	}

	public boolean addMetroUserInfo2(String username, String password, String name, String deptIds, Long roleId,
			String phoneNo, String dataRigh) {
		MetroUser user = new MetroUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setRoleId(roleId);
		user.setPhoneNo(phoneNo);
		int count = userDao.insertObj(user);
		Map<String, Object> params = new HashMap<>();
		params.put("userId", user.getId());
		params.put("deptIds", deptIds.split(","));
		count = count + userDao.addUserDeptRel(params);

		Map<String, Object> params2 = new HashMap<>();
		params2.put("userId", user.getId());
		count = count + drDao.deleteObj(params2);
		if (dataRigh != null && !"".equals(dataRigh)) {
			// String[] dataRights = dataRight.split(",");
			List<MetroUserDataRel> udrlist = new ArrayList<MetroUserDataRel>();
			MetroUserDataRel udr = null;
			for (String drt : dataRigh.split(",")) {
				String[] dr = drt.split(";");
				udr = new MetroUserDataRel();
				udr.setCityId(Long.parseLong(dr[0]));
				udr.setLineId(Long.parseLong(dr[1]));
				udr.setIntervalId(Long.parseLong(dr[2]));
				udr.setLeftOrRight(dr[3]);
				udr.setUserId(user.getId());
				udrlist.add(udr);
			}
			params2.put("udrlist", udrlist);
			count = count + drDao.insertObjs(params2);
		}
		return count > 3 ? true : false;
	}
	
	@Override
	public boolean editMetroUserInfo(String userId, String username, String name, String oldDeptIds, String deptIds,
			Long roleId, String phoneNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("username", username);
		params.put("name", name);
		params.put("roleId", roleId);
		params.put("phoneNo", phoneNo);
		int count = userDao.updateObj(params);

		if (deptIds != null) {
			params.clear();
			params.put("userId", userId);
			params.put("deptIds", oldDeptIds.split(","));
			count = count + userDao.delUserDeptRel(params);
			params.put("deptIds", deptIds.split(","));
			count = count + userDao.addUserDeptRel(params);
		}
		return count > 2 ? true : false;
	}

	@Override
	public boolean delMetroUserInfo(String deptId, String userId, int deptSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("deptId", deptId);
		int count = 0;
		if (deptSize == 1) {
			count = userDao.deleteObj(params);// 当用户只有一个部门时
		} else {
			count = userDao.deleteObjDeptRel(params);// 当用户有多个部门时
		}
		return count > 0 ? true : false;
	}

	@Override
	public boolean editMetroUserPassword(String userId, String password) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("password", password);
		return userDao.editUserPassword(params) > 0 ? true : false;
	}

	@Override
	public boolean addMetroUserToDept(String deptId, String userIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		params.put("userIds", userIds.split(","));
		return false;
	}

	@Override
	public boolean findMetroUserUsername(String username) {
		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
		return userDao.findMetroUserUsername(params) > 0 ? true : false;
	}

	@Override
	public List<MetroUser> findAllUser() {
		return null;
	}

	@Override
	public PageResultSet<MetroUser> findAllUser(String name, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		int total = userDao.countObjsr(params);
		Page page = new Page(total, pageSize, pageNum);
		if (total > 0) {
			params.put("name", name);
			params.put("start", page.getBeginIndex());
			params.put("pageSize", page.getPageSize());
			List<MetroUser> userList = userDao.findObjsListr(params);
			PageResultSet<MetroUser> pageResult = null;
			if (userList != null && userList.size() > 0) {
				pageResult = new PageResultSet<>();
				pageResult.setList(userList);
				pageResult.setPage(page);
			}
			return pageResult;
		}
		return null;
	}

	/**
	 * 通过id查询用户信息
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 */
	@Override
	public MetroUser findObjById(Long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		return userDao.findObjById(params);
	}
	
	/**
	 * 根据部门的ID取部门下所有有效用户信息
	 * @param deptId   部门ID
	 * @return
	 */
	public List<MetroUser> findAllUserByDeptId(Long deptId){
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		return userDao.findAllUserByDeptId(params);
	}

}
