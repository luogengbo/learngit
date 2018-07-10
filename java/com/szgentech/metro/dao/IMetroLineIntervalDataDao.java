package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalData;

/**
 * 地铁线路区间导向数据dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalDataDao extends BaseDao<MetroLineIntervalData> {
	
	/**
	 * 查找线路区间的左右线导向数据列表
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalData> findObjsForLineInterval(Long intervalId);
	/**
	 * 获取某线路区间左或右线的导向数据信息列表
	 * @param params  区间左或右线
	 * @return
	 */
	List<MetroLineIntervalData> findAllByIntervalIdAndLr(Map<String, Object> params);
	/**
	 * 通过唯一性条件查询唯一数据
	 * @param params
	 * @return
	 */
	MetroLineIntervalData findUniqueData(Map<String, Object> params);
	
	/**
	 * 删除
	 * 
	 * @param params
	 * @return
	 */
	boolean deleteObjall(Map<String, Object> params);
}
