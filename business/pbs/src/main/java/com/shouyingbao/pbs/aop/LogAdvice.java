package com.shouyingbao.pbs.aop;

import org.aspectj.lang.annotation.*;
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

    @Pointcut("execution(* com.shouyingbao.pbs.web.controller..*(..))")
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
        MDC.clear();
    }
}
