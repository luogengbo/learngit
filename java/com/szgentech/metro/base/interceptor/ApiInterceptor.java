package com.szgentech.metro.base.interceptor;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.szgentech.metro.base.security.MD5;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.model.MetroUser;
import com.szgentech.metro.service.impl.LoginService;
import com.szgentech.metro.vo.SessionUser;

public class ApiInterceptor implements HandlerInterceptor {
	@Autowired
	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// TODO Header or Data?
//		String u_name = request.getParameter("username");
//		String u_passwd = request.getParameter("password");
		String u_name1 = request.getHeader("username");
		String u_passwd1 = request.getHeader("password");

		MetroUser metroUser = loginService.checkUserPass(u_name1, MD5.MD5Encode(u_passwd1));
		if (metroUser != null) {
			SessionUser sUser = (SessionUser)request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			if(sUser == null || sUser.getId() != metroUser.getId()){
				SessionUser sessionUser = createSessionUser(metroUser, request);
				request.getSession().setAttribute(Constants.SESSION_CURRENT_USER, sessionUser);
			}
			return true;
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private SessionUser createSessionUser(MetroUser user, HttpServletRequest request) {
		SessionUser sessionUser = new SessionUser();
		Date date = Calendar.getInstance().getTime();
		sessionUser.setLoginTime(date);
		sessionUser.setLoginIp(getRemoteHost(request));
		sessionUser.setId(user.getId());
		sessionUser.setUsername(user.getUsername());
		sessionUser.setRoleId(user.getRoleId());
		sessionUser.setName(user.getName());
		sessionUser.setSex(user.getSex());
		return sessionUser;
	}
	
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

}
