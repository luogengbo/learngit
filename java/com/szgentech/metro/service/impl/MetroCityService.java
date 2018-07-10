package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.dao.IMetroCityDao;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.service.IMetroCityService;
/**
 * 地铁城市接口业务实现
 * @author hank
 *
 * 2016年8月16日
 */
@Service("cityService")
public class MetroCityService implements IMetroCityService{
	
	@Autowired
	private IMetroCityDao cityDao;

	/**
	 * 修改城市的工程文档url
	 * @param cityId 城市id
	 * @param projectPdfUrl 工程文档url
	 * @return
	 */
	@Override
	public boolean editProjectPdfUrl(Long cityId, String projectPdfUrl) {
		Map<String, Object> params = new HashMap<>();
		params.put("cityId", cityId);
		params.put("projectPdfUrl", projectPdfUrl);
		return cityDao.editProjectPdfUrl(params)>0?true:false;
	}

	/**
	 * 通过id查询地铁城市信息
	 * @param cityId
	 * @return
	 */
	@Override
	public MetroCity findObjById(Long cityId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cityId", cityId);
		return cityDao.findObjById(params);
	}

}
