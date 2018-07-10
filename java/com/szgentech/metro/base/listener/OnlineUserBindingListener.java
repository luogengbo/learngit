package com.szgentech.metro.base.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.MemcachedUtil;
import com.szgentech.metro.service.ILoginService;
import com.szgentech.metro.vo.SessionUser;

/**
 * 用于记录用户登录 监听方式：session.setAttribute("username", new
 * OnlineUserBindingListener("test"));
 * 
 * @author MAJL
 * 
 */
public class OnlineUserBindingListener implements HttpSessionBindingListener {
	private static Logger logger = Logger.getLogger(OnlineUserBindingListener.class);
	private SessionUser user;

	public OnlineUserBindingListener() {
	}

	public OnlineUserBindingListener(SessionUser sessionUser) {
		this.user = sessionUser;
	}

	/**
	 * session setAttribute 触发
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// 更新用户在线
		ApplicationContext ac2 = WebApplicationContextUtils
				.getWebApplicationContext(event.getSession().getServletContext());
		ILoginService loginService = (ILoginService) ac2.getBean("loginService");
		loginService.updateUserOnlineStatus(user.getId(), user.getLoginIp(), 1);
	}

	/**
	 * 1.执行session.invalidate()时。 2.session超时，自动销毁时。
	 * 3.执行session.setAttribute("onlineUserListener", "其他对象");
	 * 或session.removeAttribute("onlineUserListener"); 将listener从session中删除时。
	 */
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.print("HttpSessionBindingEvent");
		try {
			Date now = (Date) MemcachedUtil.get(Constants.SESSION_CURRENT_USER + user.getId());
			if (now != null && user.getLoginTime().getTime() > now.getTime()) {
				// 当前用户登陆时间小于最新登录时间
				System.out.print("登陆时间" + user.getLoginTime() + "\n");
			} else {
				MemcachedUtil.remove(Constants.SESSION_CURRENT_USER + user.getId());
				// 更新用户离线
				ApplicationContext ac2 = WebApplicationContextUtils
						.getWebApplicationContext(event.getSession().getServletContext());
				ILoginService loginService = (ILoginService) ac2.getBean("loginService");
				loginService.updateUserOnlineStatus(user.getId(), user.getLoginIp(), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用于记录用户登录 监听方式"+e.getMessage());
		}

	}

}
