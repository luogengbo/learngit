package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.model.TubeMapInfo;

/**
 * 全网图数据查询
 * @author MAJL
 *
 */
public interface IMetroTubeMapDao {
	/**
	 * 查询某个区间信息
	 * @param params
	 * @return
	 */
	List<TubeMapInfo> findTubeMapInfo(Map<String, Object> params);
}
