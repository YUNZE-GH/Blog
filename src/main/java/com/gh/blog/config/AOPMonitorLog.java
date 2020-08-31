package com.gh.blog.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/20 20:21
 */
@Aspect
@Component
public class AOPMonitorLog {
    private Log log = LogFactory.getLog(this.getClass().getName());
    /**
     * 切入点
     * 匹配com.gh.blog.controller包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(public * com.gh.blog.controller..*.*(..))")
    public void executePackage() {

    }

    // 在切入点的方法run之前要干的
    @Before("executePackage()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        log.info("------------------" + signature.getName() + "方法执行前-------------------");
        log.info(signature);
        Object[] obj = joinPoint.getArgs();
        log.info(Arrays.asList(obj));
    }

    @After("executePackage()")
    public void after(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        log.info("------------------" + signature.getName() + "方法执行后-------------------");
    }


}
