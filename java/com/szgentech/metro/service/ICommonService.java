package com.szgentech.metro.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.base.model.CommonResponse;

/**
 * 公用操作业务接口
 * @author luowq
 *
 */
public interface ICommonService {
	/**
	 * 上传文件
	 * @param request
	 * @return
	 */
	CommonResponse fileUpload(HttpServletRequest request);
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	CommonResponse fileUpload(MultipartFile file);
	
	/**
	 * 上传APP安装包
	 * @param file
	 * @return
	 */
	CommonResponse fileUploadApp(MultipartFile file);
}
