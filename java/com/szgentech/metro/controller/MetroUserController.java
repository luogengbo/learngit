package com.szgentech.metro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.mv.JModelAndView;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.security.MD5;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.model.MetroDept;
import com.szgentech.metro.model.MetroRole;
import com.szgentech.metro.model.MetroUser;
import com.szgentech.metro.model.UserAuthority;
import com.szgentech.metro.service.IMetroDeptService;
import com.szgentech.metro.service.IMetroRoleService;
import com.szgentech.metro.service.IMetroUserService;
import com.szgentech.metro.vo.SessionUser;

@Controller
@RequestMapping("/user")
public class MetroUserController extends BaseController {
	@Autowired
	private IMetroUserService userService;

	@Autowired
	private IMetroDeptService deptService;

	@Autowired
	private IMetroRoleService roleService;

	/**
	 * 用户管理主页
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "部门查询")
	@RequestMapping("/index")
	public String list() {
		// 查询所有机构
		List<MetroDept> dlist = deptService.findAllDeptInfo();
		modelMap.addAttribute("dlist", dlist);
		return "/sysm/smf_user";
	}

	/**
	 * 发短信（查询部门）
	 * 
	 * @return
	 */
	@RequestMapping("/index/alarm")
	@ResponseBody
	public ModelMap list2() {
		// 查询所有部门
		List<MetroDept> dlist = deptService.findAllDeptInfo();
		ModelMap mapa = new ModelMap();
		mapa.addAttribute("dlist", dlist);
		return mapa;
	}
	
	/**
	 * 发短信（查询部门下员工信息）
	 * 
	 * @return
	 */
	/*
	@RequestMapping("/index/alarm/name")
	@ResponseBody
	public ModelMap list3(@RequestParam("dept_name") String dept_name) {
		// 查询所有部门下的用户
		List<MetroUser> dlist = userService.findUsernameList(dept_name);
		ModelMap mapa = new ModelMap();
		mapa.addAttribute("dlist", dlist);
		return mapa;
	}
	*/

	/**
	 * 部门对应的用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/to/right")
	public String userInfo(@RequestParam("deptId") Long deptId) {
		modelMap.addAttribute("deptId", deptId);
		return "/sysm/smf_user_right";
	}

	/**
	 * 查找不同部门的用户
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "部门用户查询")
	@RequestMapping("/find/users")
	@ResponseBody
	public PageResultSet<MetroUser> findUserByDeptId(@RequestParam("deptId") String deptId,
			@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {

		PageResultSet<MetroUser> userResult = userService.findMetroUserInfo(deptId, pageNum, pageSize);
		return userResult;
	}

	/**
	 * 通过用户名查找用户
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "用户搜索")
	@RequestMapping("/find/name")
	@ResponseBody
	public PageResultSet<MetroUser> findUserByUsername(String name, int pageNum, int pageSize) {
		PageResultSet<MetroUser> userResult = userService.findMetroUserInfoByName(name, pageNum, pageSize);

		return userResult;
	}

	/**
	 * 保存部门信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "部门信息保存")
	@RequestMapping("/save/dept/info")
	@ResponseBody
	public ModelMap saveDeptInfo(@RequestParam("operate") String operate) {

		boolean result = true;
		if ("add".equals(operate)) {// 添加
			String deptName = request.getParameter("deptName");
			result = deptService.addMetroDeptInfo(deptName);
		} else if ("edit".equals(operate)) {// 修改
			String deptId = request.getParameter("deptId");
			String deptName = request.getParameter("deptName");
			result = deptService.editMetroDeptInfo(deptId, deptName);
		} else {
			String deptId = request.getParameter("deptId");
			result = deptService.delMetroDeptInfo(deptId);
		}
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);

		return modelMap;
	}

	/**
	 * 用户编辑页面
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "用户信息编辑")
	@RequestMapping("/to/edit")
	public ModelAndView editRoleInfo(@RequestParam("operate") String operate, @RequestParam("userId") Long userId,
			@RequestParam("deptId") Long deptId) {

		JModelAndView mv = new JModelAndView("/sysm/smf_user_edit", request, response);
		// 查询所有机构信息
		List<MetroDept> dlist = deptService.findAllDeptInfo();
		mv.addObject("dlist", dlist);
		// 查询所有角色信息 超级管理员除外
		List<MetroRole> rlist = roleService.findAllRoleInfo();
		mv.addObject("rlist", rlist);
		mv.addObject("deptId", deptId);
		if ("1".equals(operate) && userId != null) {
			MetroUser user = userService.findObjById(userId);
			mv.addObject("user", user);
		}
		return mv;
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "用户信息保存")
	@RequestMapping("/save/user/info")
	@ResponseBody
	public ModelMap saveUserInfo(
			@RequestParam("operate") String operate,
			@RequestParam("username") String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam("name") String name,
			@RequestParam("deptIds") String deptIds,
			@RequestParam("roleId") Long roleId,
			@RequestParam("phoneNo") String phoneNo,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "oldDeptIds", required = false) String oldDeptIds) {

		boolean result = true;

		ModelMap modelMap = new ModelMap();
		if ("0".equals(operate)) {// 添加
			boolean b = userService.findMetroUserUsername(username);
			if (b) {
				modelMap.addAttribute("code", 1);
			} else {
				SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
				String founder =sessionUser.getName();
				result = userService.addMetroUserInfo(username, MD5.MD5Encode(password), name, deptIds, roleId, phoneNo, founder);
			}
		} else if ("1".equals(operate)) {// 修改
			result = userService.editMetroUserInfo(userId, username, name, oldDeptIds, deptIds, roleId, phoneNo);
		}

		modelMap.addAttribute("result", result);
		return modelMap;
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 */
	@RequestMapping("/save/user/info-all")
	@ResponseBody
	public ModelMap saveUserInfoAll(@RequestParam("authorities")List<UserAuthority> authorities) {
//		List<UserAuthority> authorities = new ArrayList<>();
//		UserAuthority username2 = new UserAuthority();
//		username2.setName("hjf2");
//		username2.setUsername("hjf");
//		username2.setPassword("hjf");
//		username2.setPhoneNo("hjf");
//		username2.setRoleId((long) 2);
//		username2.setDeptIds("1");
////		username2.setDataRight("1;12;18;l,1;12;18;r,1;12;23;l,1;12;23;r,1;12;35;l,1;12;35;r,1;12;36;l,1;12;36;r,1;15;37;l,1;15;37;r,1;15;38;l,1;15;38;r,1;16;40;l,1;16;40;r");
//		username2.setDataRight("1;12;18;l,1;12;18;r,1;12;23;l,1;12;23;r,1;12;35;l,1;12;35;r,1;12;36;l,1;12;36;r");
//		authorities.add(username2);
//		
//		UserAuthority username3 = new UserAuthority();
//		username3.setName("hjf3");
//		username3.setUsername("hjf");
//		username3.setPassword("hjf");
//		username3.setPhoneNo("hjf");
//		username3.setRoleId((long) 2);
//		username3.setDeptIds("1");
//		username3.setDataRight("1;12;18;l,1;12;18;r,1;12;23;l,1;12;23;r,1;12;35;l,1;12;35;r,1;12;36;l,1;12;36;r,1;15;37;l,1;15;37;r,1;15;38;l,1;15;38;r,1;16;40;l,1;16;40;r");
////		username3.setDataRight("1;12;18;l,1;12;18;r,1;12;23;l,1;12;23;r,1;12;35;l,1;12;35;r,1;12;36;l,1;12;36;r");
//		authorities.add(username3);
		
		UserAuthority username = new UserAuthority();
		boolean result = false;
		int a = 0;
		ModelMap modelMap = new ModelMap();
		for (UserAuthority userAuthority : authorities) {
			username.setName(userAuthority.getName());
			boolean b = userService.findMetroUserUsername(username.getName());
			if(b){
				a++;
				modelMap.addAttribute(username.getName(), "用户已存在");
			}
		}
		if(a==0){
			for (UserAuthority userAuthority : authorities) {
				username.setName(userAuthority.getName());
				username.setUsername(userAuthority.getUsername());
				username.setPassword(userAuthority.getPassword());
				username.setPhoneNo(userAuthority.getPhoneNo());
				username.setRoleId(userAuthority.getRoleId());
				username.setDeptIds(userAuthority.getDeptIds());
				username.setDataRight(userAuthority.getDataRight());
				result = userService.addMetroUserInfo2(username.getName(), MD5.MD5Encode(username.getPassword()),
						username.getName(), username.getDeptIds(), username.getRoleId(), username.getPhoneNo(),
						username.getDataRight());
			}
		}
		modelMap.addAttribute("result", result);
		return modelMap;
	}
	
	
	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "用户删除")
	@RequestMapping("/del/user")
	@ResponseBody
	public ModelMap delUserInfo(@RequestParam("deptId") String deptId, @RequestParam("userId") Long userId) {
		MetroUser u = userService.findObjById(userId);
		boolean result = userService.delMetroUserInfo(deptId, userId.toString(),
				u.getDeptList() == null ? StringUtil.nullToInt(null) : u.getDeptList().size());
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);
		return modelMap;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", operate = "用户密码重置")
	@RequestMapping("/reset/user/pass")
	@ResponseBody
	public ModelMap resetUserPassword(
			@RequestParam("userId") String userId,
			@RequestParam(value = "password", required = false) String password) {
		
		if (password == null || "".equals(password)) {
			password = String.valueOf(nextInt(100000, 999999));
		}
		boolean result = userService.editMetroUserPassword(userId, MD5.MD5Encode(password));
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("password", password);
		modelMap.addAttribute("result", result);
		return modelMap;
	}

	/**
	 * 检查用户账号是否存在
	 * 
	 * @return
	 */
	@RequestMapping("/check/user/username")
	@ResponseBody
	public ModelMap checkUsername(@RequestParam("username") String username) {

		boolean result = userService.findMetroUserUsername(username);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);
		return modelMap;
	}

	public int nextInt(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
}
