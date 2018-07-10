package com.szgentech.metro.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.listener.OnlineUserBindingListener;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.security.MD5;
import com.szgentech.metro.base.security.VCode;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.MemcachedUtil;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroSysMenu;
import com.szgentech.metro.model.MetroUser;
import com.szgentech.metro.service.ILoginService;
import com.szgentech.metro.service.IMetroCityService;
import com.szgentech.metro.service.IMetroUserService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.SessionUser;

/**
 * 登录控制器
 * 
 * @author hank
 *
 *         2016年8月15日
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;

	@Autowired
	private ISysRightService rightService;

	@Autowired
	private IMetroUserService userService;

	@Autowired
	private IMetroCityService cityService;

	/**
	 * 登陆页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String toLogin() {
		MetroCity city = cityService.findObjById(1L);
		modelMap.addAttribute("login_out", request.getAttribute("login_out"));
		modelMap.addAttribute("login_msg", request.getAttribute(Constants.MSG));
		modelMap.addAttribute("project_title", city.getProjectTitle());
		return "/login/login";
	}

	/**
	 * 退出系统
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		session.removeAttribute(Constants.SESSION_CURRENT_USER);
		session.invalidate();
		return "forward:/login/index";
	}

	/**
	 * 获取验证码
	 */
	@RequestMapping(value = "/get/vcode/img", method = RequestMethod.GET)
	public void getVCodeImg() {
		// 将四位数字的验证码保存到Session中。

		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 将图像输出到Servlet输出流中
		try {
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(VCode.getVCodeImg(session), "jpeg", sos);
			sos.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取验证码:"+e.getMessage());
		}
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	@RequestMapping("/check/login")
	@ResponseBody
	public CommonResponse checkLogin(
			@RequestParam("username") String u_name,
			@RequestParam("password") String u_pass,
			@RequestParam("vcode") String u_vcode) {
		
		CommonResponse commonResponse = new CommonResponse();
		try {
			String vcode = (String) request.getSession().getAttribute(Constants.SESSION_VCODE_CODE);
			Long startT = (Long) request.getSession().getAttribute(Constants.SESSION_VCODE_TIME);
			if (vcode.equals(u_vcode.toUpperCase())) {
				long s = (System.currentTimeMillis() - startT) / 1000 / 60;
				// 验证码有效时间为3分钟
				if (s < 3) {
					MetroUser muser = loginService.checkUserPass(u_name, MD5.MD5Encode(u_pass));
					if (muser != null) {
						SessionUser sessionUser = createSessionUser(muser);
						request.getSession().setAttribute(Constants.SESSION_CURRENT_USER, sessionUser);
						try {
							/*
							 * Object obj = request.getSession().getServletContext().getAttribute(Constants.
							 * SESSION_CURRENT_USER+muser.getId()); if(obj!=null&&!"".equals(obj)){
							 * HttpSession se = (HttpSession) obj;
							 * se.removeAttribute(Constants.SESSION_CURRENT_USER); session.invalidate(); }
							 */
							MemcachedUtil.put(Constants.SESSION_CURRENT_USER + muser.getId(),
									sessionUser.getLoginTime());

						} catch (Exception e) {
							System.out.print("memche_error:" + e.getMessage());
							logger.error("用户登录checkLogin:"+e.getMessage());
						}
						request.getSession().setAttribute(Constants.SESSION_LISTENER_MARK,
								new OnlineUserBindingListener(sessionUser));
						commonResponse.setCode(Constants.CODE_SUCCESS);
					} else {
						commonResponse.setCode(Constants.CODE_FAIL);
						commonResponse.setResult(Constants.USER_ERROR_NAME_OR_PSW_MSG);
					}
				} else {// 验证码超时失效
					commonResponse.setCode(4);
					commonResponse.setResult(Constants.VCODE_OVERTIME_MSG);
				}
			} else {// 验证码错误
				commonResponse.setCode(3);
				commonResponse.setResult(Constants.VCODE_ERROR_MSG);
			}

		} catch (Exception e) {
			logger.error("系统繁忙", e);
			commonResponse.setCode(5);
			commonResponse.setResult("系统繁忙");
		}
		return commonResponse;
	}

	@RequestMapping("/to-main")
	public String toMain() {
		List<MetroSysMenu> menus = rightService.getRightMenusByRoleId(this.getCurrentUser().getRoleId());
		modelMap.addAttribute("menus", menus);
		modelMap.addAttribute("name", this.getCurrentUser().getName());
		modelMap.addAttribute("username", this.getCurrentUser().getUsername());
		MetroCity city = cityService.findObjById(1L);
		modelMap.addAttribute("project_title", city.getProjectTitle());
		return "/login/main";
	}

	private SessionUser createSessionUser(MetroUser user) {
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

	/**
	 * 修改密码主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/pass/edit/index")
	public String updatePassword() {
		modelMap.addAttribute("name", this.getCurrentUser().getName());
		return "/login/change_password";
	}

	/**
	 * 保存新密码
	 */
	@RequestMapping("/save/edit/pass/info")
	@ResponseBody
	public ModelMap saveNewPassword(
			@RequestParam("oldpass") String oldpass,
			@RequestParam("newpass") String newpass) {
		
		ModelMap modelMap = new ModelMap();
		MetroUser u = loginService.checkUserPass(this.getCurrentUser().getUsername(), MD5.MD5Encode(oldpass));
		if (u != null) {
			boolean b = userService.editMetroUserPassword(this.getCurrentUser().getId().toString(),
					MD5.MD5Encode(newpass));
			if (b) {
				modelMap.addAttribute("code", 1);
			} else {
				modelMap.addAttribute("code", 0);
			}
		} else {
			modelMap.addAttribute("code", 2);
		}
		return modelMap;
	}


	/**
	 * APP用户登录
	 * 
	 * @return
	 */
	@RequestMapping("/app/check/login")
	@ResponseBody
	public CommonResponse checkLogin(
			@RequestParam("username") String u_name,
			@RequestParam("password") String u_pass) {

		CommonResponse commonResponse = new CommonResponse();
		try {
				MetroUser muser = loginService.checkUserPass(u_name, u_pass);
				if (muser != null) {
					SessionUser sessionUser = createSessionUser(muser);
					request.getSession().setAttribute(Constants.SESSION_CURRENT_USER2, sessionUser);
					try {

						Object obj = request.getSession().getServletContext()
								.getAttribute(Constants.SESSION_CURRENT_USER2 + muser.getId());
						if (obj != null && !"".equals(obj)) {
							HttpSession se = (HttpSession) obj;
							se.removeAttribute(Constants.SESSION_CURRENT_USER2);
							session.invalidate();
						}

						MemcachedUtil.put(Constants.SESSION_CURRENT_USER2 + muser.getId(),
								sessionUser.getLoginTime());

					} catch (Exception e) {
						System.out.print("memche_error:" + e.getMessage());
						logger.error("APP用户登录checkLogin:"+e.getMessage());
					}
					request.getSession().setAttribute(Constants.SESSION_LISTENER_MARK,
							new OnlineUserBindingListener(sessionUser));
					//成功返回
					commonResponse.setResult(sessionUser);
					commonResponse.setCode(Constants.CODE_SUCCESS);
			} else {
				//用户名或密码错误
				commonResponse.setCode(Constants.CODE_FAIL);
			}

		} catch (Exception e) {
			logger.error("系统繁忙", e);
			commonResponse.setCode(5);
		}
		return commonResponse;
	}


	/**
	 * APP保存新密码
	 */
	@RequestMapping("/app/save/edit/pass/info")
	@ResponseBody
	public CommonResponse saveNewPassword(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("userID") String userID,
			@RequestParam("pwd") String pwd) {
		CommonResponse commonResponse = new CommonResponse();
		MetroUser u = loginService.checkUserPass(username, MD5.MD5Encode(password));
		if (u != null) {
			Long aa = u.getId();
			String userid = String.valueOf(aa);
			if(userid.equals(userID)) {
				boolean b = userService.editMetroUserPassword(userID, MD5.MD5Encode(pwd));
				if (b) {
					commonResponse.setCode(1);//修改成功
				} else {
					commonResponse.setCode(2);//修改失败
				}
			}else {
				commonResponse.setCode(4);//用户不存在
			}
		} else {
			commonResponse.setCode(3);//旧密码输入错误
		}
		return commonResponse;
	}


	
	/**
	 * APP退出系统
	 */
	@RequestMapping(value = "/app/logout", method = RequestMethod.GET)
	public void logoutapp() {
		session.removeAttribute(Constants.SESSION_CURRENT_USER2);
		session.invalidate();
	}

}
