package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroPhoto;

public interface IMetroPhotoDao extends BaseDao<MetroPhoto>{

	/**
	 * 查找某种类型的的所有图片
	 * @param photoType
	 * @return
	 */
	List<MetroPhoto> findByType(int photoType);

	List<MetroPhoto> findObjsListByIntervalId(Map<String, Object> params);

	MetroPhoto findKnifePhoto(Map<String, Object> params);

	MetroPhoto findSpiralPhoto(Map<String, Object> params);

	MetroPhoto findSlurryPhoto(Map<String, Object> params);
}
