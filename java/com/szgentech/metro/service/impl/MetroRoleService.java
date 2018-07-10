package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.dao.IMetroRoleDao;
import com.szgentech.metro.model.MetroRole;
import com.szgentech.metro.service.IMetroRoleService;

@Service("roleService")
public class MetroRoleService extends BaseService<MetroRole> implements IMetroRoleService {
	@Autowired
	private IMetroRoleDao roleDao;
	
	@Override
	public PageResultSet<MetroRole> findRoleList(int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		return this.getPageResultSet(params, pageNum, pageSize, roleDao);
	}

	@Override
	public boolean addRole(String roleCode, String roleName, String isUsed,
			String roleDescribe, String menuIds) {
		MetroRole mr = new MetroRole();
		mr.setRoleCode(roleCode);
		mr.setRoleName(roleName);
		mr.setIsUsed(StringUtil.nullToInt(isUsed));
		mr.setRoleDescribe(roleDescribe);
		int count = roleDao.insertObj(mr);
		if(count>0&&menuIds!=null&&!"".equals(menuIds)){
			Map<String, Object> params = new HashMap<>();
			params.put("roleId", mr.getId());
			params.put("menuIds", menuIds.split(","));
			count = count + roleDao.addRoleAndMenuRel(params);
			if(count>1){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean editRole(String roleId, String roleCode, String roleName,
			String isUsed, String roleDescribe, String oldMenuIds,
			String menuIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("roleId", roleId);
		params.put("roleCode", roleCode);
		params.put("roleName", roleName);
		params.put("isUsed", isUsed);
		params.put("roleDescribe", roleDescribe);
		int count = roleDao.updateObj(params);
		if(count>0){
			params.clear();
			params.put("roleId", roleId);
//			params.put("menuIds", oldMenuIds.split(","));
			count = count + roleDao.delRoleAndMenuRel(params);
			if(menuIds!=null&&!"".equals(menuIds)){
				params.put("menuIds", menuIds.split(","));
				count = count + roleDao.addRoleAndMenuRel(params);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean delRole(String roleId) {
		Map<String, Object> params = new HashMap<>();
		params.put("roleId", roleId);
		int count = roleDao.deleteObj(params);
		if(count>0){
			count = count + roleDao.delRoleAndMenuRel(params);
			return true;
		}
		return false;
	}

	@Override
	public MetroRole findRoleById(String roleId) {
		Map<String, Object> params = new HashMap<>();
		params.put("roleId", roleId);
		return roleDao.findObjById(params);
	}

	@Override
	public List<MetroRole> findAllRoleInfo() {
		return roleDao.findAllRoleInfo();
	}

}
