package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroDictionary;

/**
 * 字典表数据处理接口
 * @author MAJL
 *
 */
public interface IMetroDictionaryDao extends BaseDao<MetroDictionary> {
	/**
	 * 通过名称代号查询字典对象
	 * @param dicName
	 * @return
	 */
	MetroDictionary selectByName(String dicName);

	Integer checkPhotoName(Map<String, Object> params);
	
	/**
	 * 通过名称代号查询字典对象列表
	 * @param  dicNameList
	 * @return
	 */
	List<MetroDictionary> findDictionaryList(List<String> dicNameList);

}
