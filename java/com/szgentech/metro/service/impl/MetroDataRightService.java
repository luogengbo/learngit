package com.szgentech.metro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.dao.IMetroDataRightDao;
import com.szgentech.metro.model.MetroUserDataRel;
import com.szgentech.metro.service.IMetroDataRightService;

@Service("dataRightService")
public class MetroDataRightService implements IMetroDataRightService {
	@Autowired
	private IMetroDataRightDao drDao;
	
	@Override
	public List<MetroUserDataRel> findUserDataRightByUserId(Long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);		
		return drDao.findObjsList(params);
	}

	@Override
	public boolean saveDataRightInfo(Long userId, String dataRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		drDao.deleteObj(params);
		if(dataRight!=null&&!"".equals(dataRight)){
			// String[] dataRights = dataRight.split(",");
			List<MetroUserDataRel> udrlist = new ArrayList<MetroUserDataRel>();
			MetroUserDataRel udr = null;
			for(String drt:dataRight.split(",")){
				String[] dr = drt.split(";");
				udr = new MetroUserDataRel();
				udr.setCityId(Long.parseLong(dr[0]));
				udr.setLineId(Long.parseLong(dr[1]));
				udr.setIntervalId(Long.parseLong(dr[2]));
				udr.setLeftOrRight(dr[3]);
				udr.setUserId(userId);
				udrlist.add(udr);
			}
			params.put("udrlist", udrlist);
			drDao.insertObjs(params);
			
		}
		return true;
	}

}
