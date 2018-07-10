package com.szgentech.metro.service;

import com.szgentech.metro.model.MetroSmsRec;

public interface IMetroSmsRecService {
	
	/**
	 * 插入一条短信发送记录
	 * @param smsRec
	 * @return
	 */
	public int addMetroSmsRec(MetroSmsRec smsRec);

}
