package com.szgentech.metro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.dao.IMetroSmsRecDao;
import com.szgentech.metro.model.MetroSmsRec;
import com.szgentech.metro.service.IMetroSmsRecService;

@Service("smsRecService")
public class MetroSmsRecService implements IMetroSmsRecService {
	
	@Autowired
	private IMetroSmsRecDao smsRecDao;
	
	/**
	 * 插入一条短信发送记录
	 * @param smsRec
	 * @return
	 */
	public int addMetroSmsRec(MetroSmsRec smsRec){
		return smsRecDao.insertObj(smsRec);
	}

}
