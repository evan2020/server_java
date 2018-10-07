package com.shalou.demo.controller;

import com.shalou.demo.domain.LitterHelperUser;
import com.shalou.demo.repository.LitterHelperRespository;
import com.shalou.demo.service.LitterHelperService;
import com.shalou.demo.utils.ResultUtil;
import com.shalou.demo.utils.SendHttpRequest;
import com.shalou.demo.utils.XmlOrMapToggle;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.shalou.demo.utils.HttpClientUtils.postXML;
import static com.shalou.demo.utils.signUtils.createSign;

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
    public Object index(@RequestParam("openid") String openid) {
        logger.info("openid>>" + openid);
        return "/index";
    }

    //极客小助手(服务号)微信登录授权
    @GetMapping(value = "/litter/login")
    //@ResponseBody
    public Object login(LitterHelperUser litterHelperUser,
                        @RequestParam("code") String code,
                        @RequestParam("state") String state) {
        String res = litterHelperService.login(code, state);
        logger.info("res>>>>>>>>>>>>>>>>" + res);
        //如果授权成功,则跳转到首页
        if (!res.isEmpty()) {
            //return "/index";
            //重定向(把需要的参数带进去,比如token)
            return "redirect:http://dsx2016.s1.natapp.cc/shalou/litter/index?openid=" + res;
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
    public Object getConfig(@RequestParam("timeStamp") String timestamp,
                            @RequestParam("nonceStr") String nonceStr,
                            @RequestParam("url") String url,
                            @RequestParam("openid") String openid) {
        logger.info("timestamp+{}" + timestamp);
        logger.info("nonceStr+{}" + nonceStr);
        logger.info("url+{}" + url);
        logger.info("openid+{}" + openid);
        //return "";
        //返回config需要的参数
        return litterHelperService.getConfig(timestamp, nonceStr, url, openid);
    }

    //将map转为json
    @GetMapping(value = "/testJson")
    @ResponseBody
    public Object testJson() {
        Map config = new HashMap();
        config.put("timestamp", "12313");
        config.put("noncestr", "sjkfa");
        JSONObject jsonTest = JSONObject.fromObject(config);
        return jsonTest;
    }

    //微信统一下单
    @GetMapping(value = "/litter/wePay")
    @ResponseBody
    public Object wePay(@RequestParam("body") String body,
                        @RequestParam("device_info") String device_info,
                        @RequestParam("nonceStr") String nonceStr,
                        @RequestParam("out_trade_no") String out_trade_no,
                        @RequestParam("total_fee") String total_fee,
                        @RequestParam("spbill_create_ip") String spbill_create_ip,
                        @RequestParam("timeStamp") String timeStamp,
                        @RequestParam("openid") String openid) {
        return litterHelperService.wePay(body, device_info, nonceStr, out_trade_no, total_fee, spbill_create_ip, timeStamp,openid);
    }


    //微信支付回调接口(必须是post)
    @PostMapping(value = "/litter/payRes")
    @ResponseBody
    public String payRes(HttpServletRequest request) {
        return litterHelperService.payRes(request);
    }

}
