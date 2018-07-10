package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroPhotoDao;
import com.szgentech.metro.model.MetroPhoto;
import com.szgentech.metro.service.IMetroPhotoService;

@Service("photoService")
public class MetroPhotoService extends BaseService<MetroPhoto> implements IMetroPhotoService {

	@Autowired
	private IMetroPhotoDao photoDao;

	@Override
	public PageResultSet<MetroPhoto> findMetroPhotos(int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		return this.getPageResultSet(params, pageNum, pageSize, photoDao);
	}

	@Override
	public boolean saveMetroPhoto(Long photoId, String photoName, String photoUrl, int photoType) {
		int count = 0;
		Map<String, Object> params = new HashMap<>();
		params.put("photoName", photoName);
		params.put("photoUrl", photoUrl);
		params.put("photoType", photoType);

		if (photoId != null) {
			params.put("photoId", photoId);
			count = photoDao.updateObj(params);
		} else {
			MetroPhoto obj = new MetroPhoto();
			obj.setPhotoName(photoName);
			obj.setPhotoUrl(photoUrl);
			obj.setPhotoType(photoType);
			count = photoDao.insertObj(obj);
		}
		return count > 0 ? true : false;
	}

	@Override
	public boolean delMetroPhoto(Long photoId) {
		Map<String, Object> params = new HashMap<>();
		params.put("photoId", photoId);
		return photoDao.deleteObj(params) > 0 ? true : false;
	}

	@Override
	public List<MetroPhoto> findMetroPhotosByParams(Long photoId) {
		Map<String, Object> params = new HashMap<>();
		params.put("photoId", photoId);
		PageResultSet<MetroPhoto> pl = this.getPageResultSet(params, 1, 10, photoDao);
		if (pl != null && pl.getList() != null) {
			return pl.getList();
		}
		return null;
	}

	/**
	 * 查找某种类型的的所有图片
	 * 
	 * @param photoType
	 * @return
	 */
	@Override
	public List<MetroPhoto> findByType(int photoType) {
		return photoDao.findByType(photoType);
	}

	@Override
	public List<MetroPhoto> findMetroPhotosByParams(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight.toLowerCase());
		return photoDao.findObjsListByIntervalId(params);
	}

	@Override
	public MetroPhoto findKnifePhoto(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight.toLowerCase());
		return photoDao.findKnifePhoto(params);
	}

	@Override
	public MetroPhoto findSpiralPhoto(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight.toLowerCase());
		return photoDao.findSpiralPhoto(params);
	}

	@Override
	public MetroPhoto findSlurryPhoto(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight.toLowerCase());
		return photoDao.findSlurryPhoto(params);
	}
}
