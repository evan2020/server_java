package com.shalou.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

//设置相关注解
@Aspect
@Component
public class httpAspect {
    //设置logger日志
    private final static Logger logger= LoggerFactory.getLogger(httpAspect.class);

    //设置公共方法(*表示GirlController里面所有的方法,也可以选择指定的方法)
    @Pointcut("execution(public * com.shalou.demo.controller.GirlController.*(..))")
    public void log(){}

    //http请求拦截
    @Before("execution(public * com.shalou.demo.controller.GirlController.*(..))")
    public void log(JoinPoint joinPoint){
        //打印日志
        logger.info("http请求拦截");

        //获取http请求携带的信息
        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();

        //获取url
        logger.info("url={}",request.getRequestURL());

        //获取method
        logger.info("method={}",request.getMethod());

        //获取ip
        logger.info("ip={}",request.getRemoteAddr());

        //获取类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());

        //获取参数
        logger.info("args={}",joinPoint.getArgs());

    }

    //http响应拦截
    @After("execution(public * com.shalou.demo.controller.GirlController.*(..))")
    public void doAfter(){
        logger.info("http响应拦截");
    }

    //获取响应的参数
    @AfterReturning(returning = "object" ,pointcut = "log()")
    public void doAfterReturning(Object object){
        //注意打印时需要将object转为string,才能看到具体信息
        logger.info("response={}",object.toString());
    }

}
