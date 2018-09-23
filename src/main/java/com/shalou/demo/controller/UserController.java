package com.shalou.demo.controller;

import com.shalou.demo.domain.User;
import com.shalou.demo.repository.UserRepository;
import com.shalou.demo.service.UserService;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

//设置API注解
@RestController
public class UserController {

    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    //自动装配用户数据接口
    @Autowired
    private UserRepository userRepository;

    //自动装配service
    @Autowired
    private UserService userService;

    //测试接口
    @GetMapping(value = "/user/test")
    public String test() {
        return "test";
    }

    //添加用户
    @PostMapping(value = "/user/addUser")
    public Object addUser(User user) throws Exception {
        return userService.addUser(user);
    }

    //查询用户(分页查询)(条件查询)
    @GetMapping(value = "/user/queryUser")
    //获取到页码和每页返回数量以及用户相关参数
    public Object queryUser(User user,
                            @RequestParam("pageIndex") Integer pageIndex,
                            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        //设置分页(默认从0开始,所以记得减一)
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);

        //获取service的条件查询结果然后分页
        Page<User> page = userRepository.findAll(userService.queryUser(user, pageIndex, pageSize), pageRequest);

        //将查询到的结果分页(不带条件查询)(暂时不用)
        //Page<User> userPage = userRepository.findAll(pageRequest);

        //返回res
        return ResultUtil.success(page);
    }


    //编辑用户
    @PostMapping(value = "/user/edit")
    public Object editUser(User user,
                           @RequestParam("id") Integer id) throws Exception {

        //返回res信息
        return userService.editUser(user, id);
    }
}
