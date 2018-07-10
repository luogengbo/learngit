package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalSa;

/**
 * 管片姿态数据dao
 * 
 * @author wangcan
 *
 *         2017年3月3日
 */
public interface IMetroLineIntervalSaDao extends BaseDao<MetroLineIntervalSa> {

	/**
	 * 查询管片姿态信息,可根据区间、左右线、开始环号、结束环号查询，查询结果根据环号排顺序
	 * 
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalSa> findLineIntervalSas(Map<String, Object> params);

	MetroLineIntervalSa findUniqueData(Map<String, Object> params);
	/**
	 * 按区间左右线删除全部数据
	 * 
	 * @param params
	 * @return
	 */
	boolean deleteObjall(Map<String, Object> params);

}
