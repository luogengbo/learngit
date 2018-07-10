package com.szgentech.metro.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.base.page.Page;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;

/**
 * 基础业务操作接口
 * 
 * @author MAJL
 *
 * @param <T>
 */
public abstract class BaseService<T> {
	/**
	 * 获取分页数据
	 * 
	 * @param params
	 *            参数map
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @param baseDao
	 *            对应的dao
	 * @return
	 */
	public PageResultSet<T> getPageResultSet(Map<String, Object> params, int pageNum, int pageSize,
			BaseDao<T> baseDao) {
		int total = baseDao.countObjs(params);
		Page page = new Page(total, pageSize, pageNum);
		PageResultSet<T> pageResult = new PageResultSet<T>();
		pageResult.setPage(page);
		if (total > 0) {
			params.put("start", page.getBeginIndex());
			params.put("pageSize", page.getPageSize());
			List<T> list = baseDao.findObjsList(params);
			if (list != null && list.size() > 0) {
				pageResult.setList(list);
			}
		}
		if (!CommonUtils.isNotNull(pageResult.getList())) {
			pageResult.setList(new ArrayList<T>());
		}
		return pageResult;
	}
}
