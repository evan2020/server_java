package com.shalou.demo.utils;

import com.shalou.demo.result.Result;

//封装resultApi res的信息类
public class ResultUtil {

    //定义一个成功的方法
    public static Result success(Object object){
        Result result=new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    //定义一个不含其它信息的成功方法
    public static Result success(){
        return success(null);
    }

    //定义一个错误提示的方法
    public static Result error(Integer code,String msg){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
