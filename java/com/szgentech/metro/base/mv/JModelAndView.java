package com.szgentech.metro.base.mv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.szgentech.metro.base.utils.HttpInclude;

/**
 * 数据列表拼装
 * 
 * @author MAJL
 *
 */
public class JModelAndView extends ModelAndView {
	/**
	 * 普通视图，根据velocity配置文件的路径直接加载视图
	 * 
	 * @param viewName
	 *            视图名称
	 */
	public JModelAndView(String viewName) {
		super.setViewName(viewName);
	}

	/**
	 * 按指定路径加载视图，如不指定则系统默认路径加载
	 * 
	 * @param viewName
	 *            视图名称
	 * @param config
	 *            商城配置
	 * @param userPath
	 *            自定义路径，和type配合使用
	 * 
	 */
	public JModelAndView(String viewName, HttpServletRequest request, HttpServletResponse response) {

		super.setViewName(viewName);
		String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
		String url = "http://" + request.getServerName();
		if (request.getServerPort() != 80) {
			url = url + ":" + request.getServerPort() + contextPath;
		} else {
			url = url + contextPath;
		}
		String webPath = url;
		super.addObject("current_webPath", webPath);

		String system_domain = "localhost";
		String serverName = request.getServerName();
		if (isIp(serverName)) {
			system_domain = serverName;
		} else {
			if (serverName.indexOf(".") == serverName.lastIndexOf(".")) {
				system_domain = serverName;
			} else {
				system_domain = serverName.substring(serverName.indexOf(".") + 1);
			}
		}
		super.addObject("domainPath", system_domain);
		super.addObject("webPath", webPath);
		super.addObject("httpInclude", new HttpInclude(request, response));
		String query_url = "";
		if (request.getQueryString() != null && !request.getQueryString().equals("")) {
			query_url = "?" + request.getQueryString();
		}
		super.addObject("current_url", request.getRequestURI() + query_url);
		boolean second_domain_view = false;
		if (serverName.indexOf("www.") < 0 && serverName.indexOf(".") >= 0
				&& serverName.indexOf(".") != serverName.lastIndexOf(".")) {
			String secondDomain = serverName.substring(0, serverName.indexOf("."));
			second_domain_view = true;// 使用二级域名访问，相关js url需要处理，避免跨域
			super.addObject("secondDomain", secondDomain);
		}
		super.addObject("second_domain_view", second_domain_view);
	}

	public static boolean isIp(String IP) {//
		boolean b = false;
		IP = IP.trim();
		if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String s[] = IP.split("\\.");
			if (Integer.parseInt(s[0]) < 255)
				if (Integer.parseInt(s[1]) < 255)
					if (Integer.parseInt(s[2]) < 255)
						if (Integer.parseInt(s[3]) < 255)
							b = true;
		}
		return b;
	}
}
