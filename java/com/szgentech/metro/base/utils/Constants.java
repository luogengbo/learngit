package com.szgentech.metro.base.utils;

public class Constants {

	/** 登录用户session标识key */
	public final static String SESSION_CURRENT_USER = "session_current_user#1";
	/** APP登录用户session标识key */
	public final static String SESSION_CURRENT_USER2 = "session_current_user#2";
	/** 返回代码key */
	public final static String CODE = "code";
	/** 返回信息key */
	public final static String MSG = "msg";
	/** 用户名或密码为空-提示代码 */
	public final static String USER_EMPTY_NAME_OR_PSW_CODE = "100";
	/** 用户名或密码为空-提示信息 */
	public final static String USER_EMPTY_NAME_OR_PSW_MSG = "用户名或密码为空";
	/** 用户名或密码错误-提示代码 */
	public final static String USER_ERROR_NAME_OR_PSW_CODE = "101";
	/** 用户名或密码错误-提示信息 */
	public final static String USER_ERROR_NAME_OR_PSW_MSG = "用户名或密码错误";

	/**
	 * 验证码
	 */
	public final static String SESSION_VCODE_CODE = "session_vcode_code";
	/**
	 * 产生验证码时间
	 */
	public final static String SESSION_VCODE_TIME = "session_vcode_time";
	/**
	 * 验证码错误
	 */
	public final static String VCODE_ERROR_MSG = "验证码错误";
	/**
	 * 验证码超时
	 */
	public final static String VCODE_OVERTIME_MSG = "验证码过期";

	/** 服务器异常-错误代码 */
	public final static String EXCEPTION_SERVER_CODE = "003";
	/** 服务器异常-错误提示 */
	public final static String EXCEPTION_SERVER_MSG = "服务器异常";

	/**
	 * 成功代码
	 */
	public final static int CODE_SUCCESS = 1;
	/**
	 * 失败代码
	 */
	public final static int CODE_FAIL = 0;

	/**
	 * 用户session活动标识
	 */
	public final static String SESSION_LISTENER_MARK = "session_listener_mark";

	/**
	 * 角色菜单权限标记
	 */
	public final static String ROLE_MENU_RIGHT = "ROLE_MENU_RIGHT";

	/**
	 * 用户数据权限标记
	 */
	public final static String USER_DATA_RIGHT = "USER_DATA_RIGHT";

	/**
	 * 预警警告级别，
	 * None：没有设置；RedMax:红色上限；OrangeMax:橙色上限；Normal：正常；OrangeMin：橙色下限；RedMin：红色下限；yellowMax：黄色上限；yellowMin：黄色下限
	 */
	public final static String WARN_LEVEL_RED_MAX = "RedMax";
	public final static String WARN_LEVEL_RED_MIN = "RedMin";
	public final static String WARN_LEVEL_ORANGE_MAX = "OrangeMax";
	public final static String WARN_LEVEL_ORANGE_MIN = "OrangeMin";
	public final static String WARN_LEVEL_YELLOW_MAX = "yellowMax";
	public final static String WARN_LEVEL_YELLOW_MIN = "yellowMin";
	public final static String WARN_LEVEL_NORMAL = "Normal";
	public final static String WARN_LEVEL_NONE = "None";
	
	/**
	 * 周期类型
	 */
	public final static String CYCLE_TYPE_DAILY = "daily";
	public final static String CYCLE_TYPE_WEEKLY = "weekly";
	public final static String CYCLE_TYPE_MONTHLY = "monthly";
	
	// 参数字典
	public final static String PARAM_A0001 = "A0001"; // 当前环号
	
	
}
