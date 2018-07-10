package com.szgentech.metro.service;

import com.szgentech.metro.model.MetroLine;

/**
 * 地铁城市线路业务接口
 * @author hank
 *
 * 2016年8月25日
 */
public interface IMetroLineService {
	
	/**
	 * 保存线路信息
	 * @param line
	 * @return
	 */
	Long insertObj(MetroLine line);
	
	/**
	 * 删除线路信息
	 * @param lineId
	 * @return
	 */
	boolean deleteObj(Long lineId);
	/**
	 * 更新线路信息
	 * @param line
	 * @return
	 */
	boolean updateObj(MetroLine line);
	/**
	 * 通过id查询地铁城市线路信息
	 * @param lineId
	 * @return
	 */
	MetroLine findObjById(Long lineId);


}
