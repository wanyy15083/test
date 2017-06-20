package com.test.aspect;

import com.alibaba.fastjson.JSON;
import com.test.entity.UserLog;
import com.test.service.TestUserService;
import com.test.utils.RequestUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志记录AOP实现
 * Created by ZhangShuzheng on 2017/3/14.
 */
@Aspect
public class LogAspect {

	private final static Logger log = LogManager.getLogger(LogAspect.class);

	// 开始时间
	private long startTime = 0L;
	// 结束时间
	private long endTime = 0L;

	@Autowired
	TestUserService testUserService;

	@Before("execution(* *..controller..*.*(..))")
	public void doBeforeInServiceLayer(JoinPoint joinPoint) {
		log.debug("doBeforeInServiceLayer");
		startTime = System.currentTimeMillis();
	}

	@After("execution(* *..controller..*.*(..))")
	public void doAfterInServiceLayer(JoinPoint joinPoint) {
		log.debug("doAfterInServiceLayer");
	}

	@Around("execution(* *..controller..*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		// 获取request
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		HttpServletRequest request = servletRequestAttributes.getRequest();

		UserLog userLog = new UserLog();
		// 从注解中获取操作名称、获取响应结果
		Object result = pjp.proceed();
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		if (method.isAnnotationPresent(ApiOperation.class)) {
			ApiOperation log = method.getAnnotation(ApiOperation.class);
			userLog.setDescription(log.value());
		}
		if (method.isAnnotationPresent(RequiresPermissions.class)) {
			RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
			String[] permissions = requiresPermissions.value();
			if (permissions.length > 0) {
				userLog.setPermissions(permissions[0]);
			}
		}
		endTime = System.currentTimeMillis();
		log.debug("doAround>>>result={},耗时：{}", result, endTime - startTime);

		userLog.setBasePath(RequestUtil.getBasePath(request));
		userLog.setIp(RequestUtil.getIpAddr(request));
		userLog.setMethod(request.getMethod());
		if (request.getMethod().equalsIgnoreCase("GET")) {
			userLog.setParameter(request.getQueryString());
		} else {
			userLog.setParameter(JSON.toJSONString(request.getParameterMap()));
		}
		userLog.setResult(JSON.toJSONString(result));
		userLog.setSpendTime((int) (endTime - startTime));
		userLog.setStartTime(startTime);
		userLog.setUri(request.getRequestURI());
		userLog.setUrl(request.getRequestURL().toString());
		userLog.setUserAgent(request.getHeader("User-Agent"));
		userLog.setUsername(request.getUserPrincipal().toString());
		testUserService.insertUserLog(userLog);
		return result;
	}

}
