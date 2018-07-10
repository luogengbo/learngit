package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroPhoto;

/**
 * 图片管理业务接口
 * @author MAJL
 *
 */
public interface IMetroPhotoService {
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageResultSet<MetroPhoto> findMetroPhotos(int pageNum, int pageSize);
	
	/**
	 * 保存图片编辑信息
	 * @param photoId 图片id
	 * @param photoName 图片名称
	 * @param photoUrl 图片访问url
	 * @param photoType 图片类型
	 * @return
	 */
	boolean saveMetroPhoto(Long photoId, String photoName, String photoUrl, int photoType);
	
	/**
	 * 删除图片信息
	 * @param photoId 图片Id
	 * @return
	 */
	boolean delMetroPhoto(Long photoId);
	
	/**
	 * 精确查找图片信息
	 * @param photoId 图片Id
	 * @return
	 */
	List<MetroPhoto> findMetroPhotosByParams(Long photoId);
	
	/**
	 * 查找某种类型的的所有图片
	 * @param photoType
	 * @return
	 */
	List<MetroPhoto> findByType(int photoType);
	
	/**
	 * @param intervalId   区间id
	 * @param leftOrRight  左右线
	 * @return
	 */
	List<MetroPhoto> findMetroPhotosByParams(Long intervalId, String leftOrRight);

	MetroPhoto findKnifePhoto(Long intervalId, String leftOrRight);

	MetroPhoto findSpiralPhoto(Long intervalId, String leftOrRight);

	MetroPhoto findSlurryPhoto(Long intervalId, String leftOrRight);

}
