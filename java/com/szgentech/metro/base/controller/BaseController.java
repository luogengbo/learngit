package com.szgentech.metro.base.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.vo.SessionUser;

/**
 * controller基类，自动注入基本对象
 * 
 * @author hank
 *
 *         2016年4月8日
 */
public class BaseController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	/**
	 * 后端传递参数对象
	 */
	protected ModelMap modelMap;
	private static Logger logger = Logger.getLogger(BaseController.class);

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		this.modelMap = modelMap;
	}

	/**
	 * 用于获取当前登录用户
	 * 
	 * @return
	 */
	protected SessionUser getCurrentUser() {
		
		Object obj	= this.session.getAttribute(Constants.SESSION_CURRENT_USER);
		Object obj2 = this.session.getAttribute(Constants.SESSION_CURRENT_USER2);
		if (obj != null) {
			return (SessionUser) obj;
		}
		if (obj2 != null) {
			return (SessionUser) obj2;
		}
		try {
			logger.warn("session过期，请重新登录");
			response.sendRedirect("/login/index");
		} catch (IOException e) {
			logger.error("session过期，重定向有误", e);
		}
		return null;
	}

}
