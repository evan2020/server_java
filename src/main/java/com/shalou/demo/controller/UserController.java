package com.shalou.demo.controller;

import com.shalou.demo.domain.User;
import com.shalou.demo.repository.UserRepository;
import com.shalou.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//设置API注解
@RestController
public class UserController {

    //引入日志
    private final static Logger logger= LoggerFactory.getLogger(UserController.class);

    //自动装配用户数据接口
    @Autowired
    private UserRepository userRepository;

    //自动装配service
    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/test")
    public String test(){
        return "test";
    }

    @PostMapping(value = "/user/addUser")
    public Object addUser(User user)throws Exception{
        return userService.addUser(user);
    }
}
