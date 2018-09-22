package com.shalou.demo.service;

import com.shalou.demo.enums.ResultEnum;
import com.shalou.demo.exception.GirlException;
import com.shalou.demo.utils.ResultUtil;
import org.springframework.stereotype.Service;


import com.shalou.demo.domain.Girl;
import com.shalou.demo.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

//中间层
@Service
public class GirlService {

    //自动装配GirlRepository
    @Autowired
    private GirlRepository girlRepository;

    //设置ResultApi返回体注解
    @ResponseBody
    public Object getAge(Integer id )throws Exception{
        //获取数据库的结果信息
        Girl girl=girlRepository.findById(id).get();
        //获取信息中的女生年龄
        Integer age=girl.getAge();
        //设定一些逻辑判断并通过捕获返回res信息
        if(age<10){
            //返回枚举中定义的的错误类型
            throw new GirlException(ResultEnum.MIN_SCHOOL);
        }else if(age>18){
            //返回枚举中定义的的错误类型
            throw new GirlException(ResultEnum.MAX_SCHOOL);
        }else{
            //返回要查询的信息
            return ResultUtil.success(girl);
        }

    }


}
