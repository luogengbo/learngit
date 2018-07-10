package com.szgentech.metro.service;

import com.szgentech.metro.vo.TubeMapIntervalLr;

/**
 * 全网图业务处理接口
 * 
 * @author MAJL
 *
 */
public interface IMetroTubeMapService {
	/**
	 * 查找区间左右线数据
	 * 
	 * @param intervalId
	 *            区间Id
	 * @return
	 */
	TubeMapIntervalLr findLrInfo(Long intervalId);
}
