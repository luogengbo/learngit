package com.szgentech.metro.base.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.model.MetroUserOperateRec;
import com.szgentech.metro.service.IMetroOperateService;
import com.szgentech.metro.vo.SessionUser;

@Aspect
@Component
public class SysControllorLogAspect {
	private static Logger logger = Logger.getLogger(SysControllorLogAspect.class);
	@Autowired
	private IMetroOperateService operateService;
	/**
	 * 日志队列
	 */
	public static Queue<MetroUserOperateRec> logsQueue = new LinkedList<MetroUserOperateRec>();

	/**
	 * Controller层切点
	 */
	@Pointcut("@annotation(com.szgentech.metro.base.aop.SysControllorLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			MetroUserOperateRec obj = getControllerMethodDescription(joinPoint);
			if (obj != null) {
				new Thread(new logt(obj)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("前置通知 用于拦截Controller层记录用户的操作"+e.getMessage());
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static MetroUserOperateRec getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		MetroUserOperateRec log = new MetroUserOperateRec();
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class<?>[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					log.setVisitMenu(method.getAnnotation(SysControllorLog.class).menu());
					log.setOperation(method.getAnnotation(SysControllorLog.class).operate());
					break;
				}
			}
		}
		SessionUser user = (SessionUser) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
		log.setUserId(user.getId());
		log.setUsername(user.getName());
		log.setLoginIp(user.getLoginIp());
		log.setVisitTime(Calendar.getInstance().getTime());
		return log;
	}

	/**
	 * 将日志对象放入队列
	 * 
	 * @param obj
	 *            日志对象
	 */
	public synchronized void add(MetroUserOperateRec obj) {
		if (logsQueue.size() > 0) {
			new Thread(new Runnable() {
				public void run() {
					synchronized ("logs-to-database") {
						List<MetroUserOperateRec> logs = new ArrayList<MetroUserOperateRec>();
						int i = 1;
						while (i > 0) {
							logs.add(logsQueue.poll());
							i--;
						}
						operateService.addLogs(logs);
					}

				}
			}).start();
			;
		}
		logsQueue.offer(obj);
	}

	/**
	 * 写日志线程
	 * 
	 * @author MAJL
	 *
	 */
	class logt implements Runnable {
		private MetroUserOperateRec log;

		public logt(MetroUserOperateRec obj) {
			this.log = obj;
		}

		@Override
		public void run() {
			add(log);
		}
	}
}
