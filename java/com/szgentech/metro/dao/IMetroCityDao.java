package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroCity;

/**
 * 地铁城市dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroCityDao extends BaseDao<MetroCity> {
	
	/**
	 * 修改城市的工程文档url
	 * @param params
	 * @return
	 */
	int editProjectPdfUrl(Map<String,Object> params);
}
