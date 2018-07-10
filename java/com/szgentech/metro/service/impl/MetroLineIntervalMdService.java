package com.szgentech.metro.service.impl;

//import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroLineIntervalMdDao;
import com.szgentech.metro.model.MetroLineIntervalMd;
import com.szgentech.metro.service.IMetroLineIntervalMdService;

/**
 * 地铁线路区间上传监测数据记录业务接口实现
 * 
 * @author hank
 *
 *         2016年8月17日
 */
@Service("lineIntervalMdService")
public class MetroLineIntervalMdService extends BaseService<MetroLineIntervalMd> implements IMetroLineIntervalMdService {

	// private static Logger logger = Logger.getLogger(MetroLineIntervalMdService.class);

	@Autowired
	private IMetroLineIntervalMdDao lineIntervalMdDao;

}
