package com.szgentech.metro.base.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 改造spring自带的MultipartResolver，解决kindeditor和自带的文件上传冲突
 * 
 * @author hank
 *
 *         2016年5月30日
 */
public class ZookeMultipartResolver extends CommonsMultipartResolver {
	private String excludeUrls;
	private String[] excludeUrlArray;

	public String getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String excludeUrls) {
		this.excludeUrls = excludeUrls;
		this.excludeUrlArray = excludeUrls.split(",");
	}

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		for (String url : excludeUrlArray) {
			if (request.getRequestURI().contains(url)) {
				return false;
			}
		}
		return super.isMultipart(request);
	}

}
