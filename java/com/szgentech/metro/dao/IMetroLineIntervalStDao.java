package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalSt;

/**
 * 盾尾间隙数据dao
 * 
 * @author wangcan
 *
 *         2017年3月3日
 */
public interface IMetroLineIntervalStDao extends BaseDao<MetroLineIntervalSt> {

	/**
	 * 查询指定区间盾尾间隙信息
	 * 
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalSt> findLineIntervalSts(Map<String, Object> params);

	MetroLineIntervalSt findUniqueData(Map<String, Object> params);
	/**
	 * 按区间左右线删除盾尾间隙全部数据
	 * 
	 * @param params
	 * @return
	 */
	boolean deleteObjall(Map<String, Object> params);
}
