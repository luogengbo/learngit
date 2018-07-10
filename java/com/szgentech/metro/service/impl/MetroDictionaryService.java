package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroDictionaryDao;
import com.szgentech.metro.model.MetroDictionary;
import com.szgentech.metro.service.IMetroDictionaryService;

@Service("dicService")
public class MetroDictionaryService extends BaseService<MetroDictionary> implements IMetroDictionaryService {
	@Autowired
	private IMetroDictionaryDao dicDao;

	@Override
	public PageResultSet<MetroDictionary> findMetroDictionaryInfo(int pageNum,
																  int pageSize) {
		Map<String, Object> params = new HashMap<>();
		return this.getPageResultSet(params, pageNum, pageSize, dicDao);
	}

	@Override
	public boolean checkPhotoName(String pname) {
		Map<String, Object> params = new HashMap<>();
		params.put("pname", pname);
		Integer count = dicDao.checkPhotoName(params);
		if(count!=null&&count>0){
			return true;
		}
		return false;
	}
	
	/**
	 * ͨ通过名称代号查询字典对象列表
	 * @return
	 */
	public List<MetroDictionary> findDictionaryList(List<String> dicNameList){
		return dicDao.findDictionaryList(dicNameList);
	}

}
