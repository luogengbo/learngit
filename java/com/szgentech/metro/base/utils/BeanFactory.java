package com.szgentech.metro.base.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring bean获取,主要用于测试使用，无需应用启动
 * 
 * @author hank
 *
 *         2016年4月8日
 */
public class BeanFactory {
	private static ApplicationContext ctx;

	public static Object getBean(String beanName) {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(
					new String[] { "classpath:/xml/applicationContext.xml", "classpath:/xml/springMVC-servlet.xml" });
		}
		return ctx.getBean(beanName);
	}

}
