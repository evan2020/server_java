package com.shalou.demo.service;

import com.shalou.demo.domain.User;
import com.shalou.demo.repository.UserRepository;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


//设置service
@Service
public class UserService {

    //引入日志
    private final static Logger logger= LoggerFactory.getLogger(UserService.class);

    //自动装配
    @Autowired
    private UserRepository userRepository;

    //设置API res (添加用户)
    @ResponseBody
    public Object addUser(User user) throws Exception {

        /*
        1.判断是否已有同名用户
        2.判断是否已注册相同手机号
        3.判断是否已注册相同邮箱
        4.id自增(不用提交ID)
        */

        //判断某个值是否为空(比如用户名),设置是否非必填字段

        //通过用户名来查询
        List userNameOne=userRepository.findUserByUserName(user.getUserName());
        //如果用户名查询的结果不为空
        if(!userNameOne.isEmpty()&&userNameOne!=null){
            return ResultUtil.error(-1, "用户名已存在");
        }
        //通过手机号来查询
        List userPhoneNumOne=userRepository.findUserByPhoneNum(user.getPhoneNum());
        //如果手机号查询的结果不为空
        if(!userPhoneNumOne.isEmpty()&&userPhoneNumOne!=null){
            return ResultUtil.error(-1, "该手机号已注册");
        }
        //通过邮箱来查询
        List userEmailOne=userRepository.findUserByUserEmail(user.getUserEmail());
        //如果邮箱查询的结果不为空
        if(!userEmailOne.isEmpty()&&userEmailOne!=null){
            return ResultUtil.error(-1, "该邮箱已注册");
        }

        //校验过后存储到数据库
        user.setUserName(user.getUserName());
        user.setPhoneNum(user.getPhoneNum());
        user.setUserEmail(user.getUserEmail());
        user.setSex(user.getSex());
        user.setCity(user.getCity());
        user.setStatus(user.getStatus());
        userRepository.save(user);

        //返回 res
        return ResultUtil.success(user);

    }

}
