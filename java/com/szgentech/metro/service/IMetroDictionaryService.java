package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroDictionary;
/**
 * 字典表业务处理接口
 * @author MAJL
 *
 */
public interface IMetroDictionaryService {
	/**
	 * 分页查询
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroDictionary> findMetroDictionaryInfo(int pageNum, int pageSize);
	
	/**
	 * 检查图片名是否存在
	 * @param pname
	 * @return
	 */
	boolean checkPhotoName(String pname);
	
	/**
	 * 通过名称代号查询字典对象列表
	 * @return
	 */
	List<MetroDictionary> findDictionaryList(List<String> dicNameList);
}
