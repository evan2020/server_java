package com.shalou.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class httpAspect {
    //设置logger日志
    private final static Logger logger= LoggerFactory.getLogger(httpAspect.class);

    @Pointcut("execution(public * com.shalou.demo.controller.GirlController.*(..))")
    public void log(){

    }

    //http请求拦截
    @Before("execution(public * com.shalou.demo.controller.GirlController.*(..))")
    public void log(JoinPoint joinPoint){
        //System.out.println("111111111111");
        logger.info("11111111111111111");

        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //url
        logger.info("url={}",request.getRequestURL());

        //method
        logger.info("method={}",request.getMethod());

        //ip
        logger.info("ip={}",request.getRemoteAddr());

        //类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());

        //参数
        logger.info("args={}",joinPoint.getArgs());

    }

    //http响应拦截
    @After("execution(public * com.shalou.demo.controller.GirlController.*(..))")
    public void doAfter(){
        //System.out.println("3333333333333");
        logger.info("3333333333333333");
    }

    //获取响应的参数
    @AfterReturning(returning = "object" ,pointcut = "log()")
    public void doAfterReturning(Object object){
        logger.info("response={}",object.toString());
    }

}
