package com.shalou.demo.controller;

import com.shalou.demo.domain.LitterHelperUser;
import com.shalou.demo.repository.LitterHelperRespository;
import com.shalou.demo.service.LitterHelperService;
import com.shalou.demo.utils.ResultUtil;
import com.shalou.demo.utils.SendHttpRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//设置API注解
@Controller
public class LitterHelperController {
    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private LitterHelperRespository litterHelperRespository;

    @Autowired
    private LitterHelperService litterHelperService;

    //授权之后重定向的页面(为了在url上携带参数)
    @GetMapping(value = "/litter/index")
    public Object index( @RequestParam("openid") String openid){
        logger.info("openid>>"+openid);
        return "/index";
    }

    //极客小助手(服务号)微信登录授权
    @GetMapping(value = "/litter/login")
    //@ResponseBody
    public Object login(LitterHelperUser litterHelperUser,
                        @RequestParam("code") String code,
                        @RequestParam("state") String state) {
        String res = litterHelperService.login(code,state);
        logger.info("res>>>>>>>>>>>>>>>>"+res);
        //如果授权成功,则跳转到首页
        if (!res.isEmpty()) {
            //return "/index";
            //重定向(把需要的参数带进去,比如token)
            return "redirect:http://dsx2016.s1.natapp.cc/shalou/litter/index?openid="+res;
        }
        //如果授权失败.则跳到错误页面
        else {
            logger.info("其他错误");
            return "/index";
        }
    }

    //获取jsapi的config参数
    @ResponseBody
    @GetMapping(value = "/getConfig")
    public Object getConfig(@RequestParam("timestamp") String timestamp,
                            @RequestParam("nonceStr") String nonceStr,
                            @RequestParam("url") String url,
                            @RequestParam("openid") String openid) {
        logger.info("timestamp+{}"+timestamp);
        logger.info("nonceStr+{}"+nonceStr);
        logger.info("url+{}"+url);
        //return "";
        //返回config需要的参数
        return litterHelperService.getConfig(timestamp,nonceStr,url,openid);
    }

    //将map转为json
    @GetMapping(value = "/testJson")
    @ResponseBody
    public Object testJson(){
        Map config = new HashMap();
        config.put("timestamp", "12313");
        config.put("noncestr", "sjkfa");
        JSONObject jsonTest = JSONObject.fromObject(config);
        return jsonTest;
    }
}
