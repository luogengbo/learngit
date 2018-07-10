package com.szgentech.metro.base.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.MemcachedUtil;
import com.szgentech.metro.vo.SessionUser;

/**
 * 访问拦截器，用于验证登录权限
 * 
 * @author hank
 *
 *         2016年7月26日
 */
public class VisitorInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(VisitorInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		SessionUser su = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		SessionUser su2 = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER2);
		if (su != null || su2 != null) { // 已登录
			try {
				if(su != null) {
					Date now = (Date) MemcachedUtil.get(Constants.SESSION_CURRENT_USER + su.getId());
					if (now != null) {
						if (su.getLoginTime().getTime() < now.getTime()) {
							logger.debug("用户ID:" + su.getId() + "  Session 登录时间:" + su.getLoginTime());
							logger.debug("用户ID:" + su.getId() + " Memcached登录时间:" + now);
							session.invalidate();
							request.setAttribute("login_out", 1);
							request.setAttribute(Constants.MSG, "您被强制下线");
							request.getRequestDispatcher("/login/index").forward(request, response);
							return false;
						}
					}
				}
				if(su2 != null) {
					Date now = (Date) MemcachedUtil.get(Constants.SESSION_CURRENT_USER2 + su2.getId());
					if (now != null) {
						if (su2.getLoginTime().getTime() < now.getTime()) {
							logger.debug("用户ID:" + su2.getId() + "  Session 登录时间:" + su2.getLoginTime());
							logger.debug("用户ID:" + su2.getId() + " Memcached登录时间:" + now);
							session.invalidate();
							request.getRequestDispatcher("9").forward(request, response);
							return false;
						}
					}
				}
			} catch (Exception e) {
				logger.error("VisitorInterceptor类:", e);
			}
			return true;
		}else { 
			// 未登录或session过期
			// response.sendRedirect(request.getContextPath()+"/user/to-error");
			request.setAttribute(Constants.MSG, "请重新登录");
			request.getRequestDispatcher("/login/index").forward(request, response);
			return false;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

}
