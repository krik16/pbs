package com.shouyingbao.pbs.aop;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Description:日志切面，注入唯一logid
 *kejun
 */
@Component
@Aspect
public class LogAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAdvice.class);
    @Pointcut("execution(* com.shouyingbao.pbs.service.impl.*.*(..))||execution(* com.shouyingbao.pbs.web.controller.*.*(..))")
    public void pointcut(){
    }

    @Before("pointcut()")
    public void before() {
        MDC.put("logid", UUID.randomUUID().toString().substring(1, 16));
    }

    @After("pointcut()")
    public void after()
    {
        MDC.clear();
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing()
    {
        LOGGER.info("afterThrowing={}",MDC.get("logid"));
        MDC.clear();
    }
}
