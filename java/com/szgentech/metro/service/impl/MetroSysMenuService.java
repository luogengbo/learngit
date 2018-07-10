package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.dao.IMetroMenuDao;
import com.szgentech.metro.model.MetroSysMenu;
import com.szgentech.metro.service.IMetroSysMenuService;

@Service("menuService")
public class MetroSysMenuService implements IMetroSysMenuService {
	
	@Autowired
	private IMetroMenuDao menuDao;
	
	@Override
	public List<MetroSysMenu> findMenuAll(int level) {
		return menuDao.findObjsList(new HashMap<String, Object>());
	}

}
