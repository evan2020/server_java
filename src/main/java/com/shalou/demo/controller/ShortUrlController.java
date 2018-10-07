package com.shalou.demo.controller;

import com.shalou.demo.domain.ShortUrl;
import com.shalou.demo.repository.ShortUrlRespository;
import com.shalou.demo.utils.ResultUtil;
import com.shalou.demo.utils.ShortUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShortUrlController {
    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ShortUrlRespository shortUrlRespository;

    //获取长链接转为短链接(建议使用post)
    @PostMapping(value = "/shortUrl")
    @ResponseBody
    public Object shortUrl(@RequestParam("url") String url) {
        logger.info(url);
        //转换成含有4个6位字符串的数组
        String[] shortUrl = ShortUrlUtils.shortUrl(url);
        //随便取其中一个作为短网址
        //从数据库查询是否已有该长链接
        ShortUrl shortUrlLong = shortUrlRespository.findShortUrlByLongUrl(url);
        //当数据不重复时才存入数据库
        if (shortUrlLong == null) {
            //将长网址和短网址存入到数据库
            ShortUrl shortUrlOne = new ShortUrl();
            //设置长链接
            shortUrlOne.setLongUrl(url);
            //设置短链接
            shortUrlOne.setShortUrl(shortUrl[0]);
            //保存到数据库
            shortUrlRespository.save(shortUrlOne);

            //返回给使用短链接的参数
            return ResultUtil.success("https://shield.smallzhiyun.com/shortUrl/" + shortUrl[0]);
        } else {
            return ResultUtil.error(-1, "短链接重复啦");
        }


    }

    //使用短链接(使用方式为/后面加上返回的参数)
    @GetMapping(value = "/shortUrl/{url}")
    public String toShortUrl(@PathVariable("url") String url) {
        //获取短链接字段
        ShortUrl shortUrl = shortUrlRespository.findShortUrlByShortUrl(url);
        //从数据库获取长链接
        String longUrl = shortUrl.getLongUrl();
        //重定向到长链接
        return "redirect:" + longUrl;
    }
}
