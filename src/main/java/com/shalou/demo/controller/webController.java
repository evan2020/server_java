package com.shalou.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webController {
    //测试接口
    @GetMapping(value = "/index")
    public String test() {
        return "/index";
    }

    //测试接口
    @GetMapping(value = "/txt")
    public String txt() {
        return "/123.txt";
    }

    //测试接口
    @GetMapping(value = "/jpg")
    public Object jpg() {
        return "/123.jpg";
    }


}
