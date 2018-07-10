package com.szgentech.metro.service;

import com.szgentech.metro.model.MetroLineIntervalPp;

/**
 * 地铁线路区间工程进度业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalPpService {

	/**
	 * 通过intervalId查询model
	 * @param intervalId
	 * @return
	 */
	MetroLineIntervalPp findByIntervalId(Long intervalId);
	
	/**
	 * 保存
	 * @param pp
	 * @return
	 */
	Long insertObj(MetroLineIntervalPp pp);
	/**
	 * 更新
	 * @param pp
	 * @return
	 */
	boolean updateObj(MetroLineIntervalPp pp);

}
