package com.shalou.demo.handle;

import com.shalou.demo.domain.Result;
import com.shalou.demo.exception.ComException;
import com.shalou.demo.exception.GirlException;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//异常信息捕获
@ControllerAdvice
public class ExceptionHandle {

    //引入日志
    private final Logger logger= LoggerFactory.getLogger(Exception.class);

    //设置异常信息注解
    @ExceptionHandler(value = Exception.class)
    //设置ResultAPi返回体的注解
    @ResponseBody
    //定义统一的异常处理方法
    public Result handle(Exception e){
        //判断
        if(e instanceof ComException){
            //如果是自定义的异常捕获,则返回自动的ResultApi信息
            ComException comException=(ComException)e;
            return ResultUtil.error(comException.getCode(),comException.getMessage());
        }else{
            //如果是系统错误,则在控制台打印日志,并返回统一的错误信息
            logger.error("[系统异常]{}",e);
            return ResultUtil.error(-1,"未知错误");
        }

    }

}
