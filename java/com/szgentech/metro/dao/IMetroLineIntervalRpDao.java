package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalRp;

/**
 * 地铁线路区间风险位置dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalRpDao extends BaseDao<MetroLineIntervalRp> {

    /**
     * 修改
     * @param params
     * @return
     */
    int updatePdf(Map<String, Object> params);

}
