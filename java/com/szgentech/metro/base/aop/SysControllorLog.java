package com.szgentech.metro.base.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysControllorLog {
	// String uid() default "";//用户Id
	// String uname() default "";//用户姓名
	// String loginip() default "";//访问Ip
	String menu() default "";// 访问菜单

	String operate() default "";// 具体操作
}
