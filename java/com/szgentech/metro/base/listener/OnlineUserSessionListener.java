package com.szgentech.metro.base.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 用于监控用户非正常退出在线状态
 * 
 * @author MAJL
 *
 */
public class OnlineUserSessionListener implements HttpSessionListener {

	public OnlineUserSessionListener() {
	}

	/**
	 * 创建session时执行
	 */
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		ServletContext application = session.getServletContext();
		Object visit_count = application.getAttribute("visit_count");// 访问量
		int count = 0;
		if (visit_count == null) {

		} else {

		}
		application.setAttribute("visit_count", count);
	}

	/**
	 * 销毁session时执行
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {

	}

}
