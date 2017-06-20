package com.test.aspect;

import com.alibaba.dubbo.rpc.RpcContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;

/**
 * rpc提供者和消费者日志打印
 * Created by ZhangShuzheng on 2017/4/19.
 */
public class RpcLogAspect {

    private static Logger log = LogManager.getLogger(RpcLogAspect.class);

    // 开始时间
    private long startTime = 0L;
    // 结束时间
    private long endTime = 0L;

    @Before("execution(* *..service.impl..*.*(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        log.debug("doBeforeInServiceLayer");
        startTime = System.currentTimeMillis();
    }

    @After("execution(* *..service.impl..*.*(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        log.debug("doAfterInServiceLayer");
    }

    @Around("execution(* *..service.impl..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        endTime = System.currentTimeMillis();
        log.debug("doAround>>>result={},耗时：{}", result, endTime - startTime);
        // 是否是消费端
        boolean consumerSide = RpcContext.getContext().isConsumerSide();
        // 获取最后一次提供方或调用方IP
        String ip = RpcContext.getContext().getRemoteHost();
        // 服务url
        String rpcUrl = RpcContext.getContext().getUrl().getParameter("application");
        log.info("consumerSide={}, ip={}, url={}", consumerSide, ip, rpcUrl);
        return result;
    }

}
