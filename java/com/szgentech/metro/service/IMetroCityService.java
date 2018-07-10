package com.szgentech.metro.service;

import com.szgentech.metro.model.MetroCity;

/**
 * 地铁城市业务接口
 * 
 * @author hank
 *
 *         2016年8月16日
 */
public interface IMetroCityService {

	/**
	 * 修改城市的工程文档url
	 * 
	 * @param id
	 *            城市id
	 * @param projectPdfUrl
	 *            工程文档url
	 * @return
	 */
	boolean editProjectPdfUrl(Long id, String projectPdfUrl);

	/**
	 * 通过id查询地铁城市信息
	 * 
	 * @param cityId
	 * @return
	 */
	MetroCity findObjById(Long cityId);

}
