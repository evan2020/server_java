package com.shalou.demo.service;

import com.google.gson.Gson;
import com.shalou.demo.domain.LitterHelperUser;
import com.shalou.demo.repository.LitterHelperRespository;
import com.shalou.demo.utils.ResultUtil;
import com.shalou.demo.utils.SHA1;
import com.shalou.demo.utils.SendHttpRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//设置service
@Service
public class LitterHelperService {
    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private LitterHelperRespository litterHelperRespository;

    //极客小助手(服务号)微信登录授权
    //@ResponseBody
    public String login(String code,String state) {
        //获取登录授权的token(与jsapi token不一样)
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxdfe8e9a69851a406&secret=16007b600d84658cbbe894488efb9940&code=" + code + "&grant_type=authorization_code";
        //另外一种请求方式
        JSONObject json = SendHttpRequest.sendGet(url);
        //获取到openid
        String login_openid = json.getString("openid");
        //获取到access_token
        String login_access_token = json.getString("access_token");
        //设置微信语言
        String login_lang = "zh_CN";
        //设置获取用户信息url
        String userInfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + login_access_token + "&openid=" + login_openid + "&lang=" + login_lang;
        //先判断数据库是都已有次用户
        LitterHelperUser userOne = litterHelperRespository.findLitterHelperUserByOpenid(login_openid);
        //如果查询当前用户不存在,则添加用户
        if (userOne == null) {
            logger.info("当前用户为null");
            //获取用户信息
            JSONObject userInfo = SendHttpRequest.sendGet(userInfo_url);
            //new一个新的user
            LitterHelperUser addUserOne = new LitterHelperUser();
            try {
                //转换微信响应的数据格式
                String openid = new String(userInfo.getString("openid").getBytes("ISO-8859-1"), "UTF-8");
                String nickname = new String(userInfo.getString("nickname").getBytes("ISO-8859-1"), "UTF-8");
                String sex = new String(userInfo.getString("sex").getBytes("ISO-8859-1"), "UTF-8");
                String province = new String(userInfo.getString("province").getBytes("ISO-8859-1"), "UTF-8");
                String city = new String(userInfo.getString("city").getBytes("ISO-8859-1"), "UTF-8");
                String country = new String(userInfo.getString("country").getBytes("ISO-8859-1"), "UTF-8");
                String headimgurl = new String(userInfo.getString("headimgurl").getBytes("ISO-8859-1"), "UTF-8");
                String privilege = new String(userInfo.getString("privilege").getBytes("ISO-8859-1"), "UTF-8");
                try {
                    String unionid = new String(userInfo.getString("unionid").getBytes("ISO-8859-1"), "UTF-8");
                    addUserOne.setNickname(unionid);
                } catch (Exception e) {
                    //e.printStackTrace();
                }

                //设置将要保存的user
                addUserOne.setOpenid(openid);
                addUserOne.setNickname(nickname);
                addUserOne.setSex(sex);
                addUserOne.setProvince(province);
                addUserOne.setCity(city);
                addUserOne.setCountry(country);
                addUserOne.setHeadimgurl(headimgurl);
                addUserOne.setPrivilege(privilege);
                addUserOne.setRedirectSate(state);

                //保存到数据库
                litterHelperRespository.save(addUserOne);
                return login_openid;
            } catch (Exception e) {
                //e.printStackTrace();
                logger.info("新增用户数据失败");
                return login_openid;
            }
        }
        //如果当前用户存在则打印当前用户信息
        else {
            logger.info("检查数据库中的用户+{}" + userOne.toString());
            return login_openid;
        }
    }

    //获取jsapi的config参数
    //@ResponseBody
    public Object getConfig(String timestamp, String nonceStr, String url, String openid) {
        Integer timeStart = 0;
        Integer timeNow = 0;
        //先判断数据库是都已有次用户
        LitterHelperUser userOne = litterHelperRespository.findLitterHelperUserByOpenid(openid);
        //获取创建access_token_time的时间戳
        String access_token_time = userOne.getAccessTokenTime();
        if (access_token_time == null || access_token_time == "" || access_token_time.isEmpty()) {
            timeStart = Integer.valueOf(timestamp) - 7500;
        } else {
            //把创建的时间戳转为数字
            timeStart = Integer.valueOf(access_token_time);
        }

        if (timestamp == null || timestamp == "" || timestamp.isEmpty()) {

        } else {
            //把当前的时间戳转为数字
            timeNow = Integer.valueOf(timestamp);
        }


        //判断超时时间(7200秒有效期)
        //大于7000设置token失效
        if (timeNow - timeStart > 7000) {
            //重新获取token
            logger.info("初始化ticket");
            //初始化签名变量
            String signature = "";
            //获取jsapi中的token( 需要在公众号中设置ip白名单才能刷新token)
            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxdfe8e9a69851a406&secret=16007b600d84658cbbe894488efb9940";
            //获取access_token(有效期2个小时)
            JSONObject json = SendHttpRequest.sendGet(tokenUrl);
            logger.info(json.toString());
            String access_token = json.getString("access_token");
            //获取ticket_url(有效期2个小时)
            String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
            String jsapi_ticket = SendHttpRequest.sendGet(ticket_url).getString("ticket");
            logger.info("jsapi_ticket" + jsapi_ticket);
            //jsapi签名(其他三个参数由前端传递过来,保持一致)
            String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + nonceStr + "&timestamp=" + timestamp
                    + "&url=" + url + "";
            try {
                //获取加密签名
                signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
                logger.info("signature" + signature);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //把数据保存到数据库
            userOne.setAccessToken(access_token);
            userOne.setAccessTokenTime(timestamp);
            userOne.setJsapiTicket(jsapi_ticket);
            userOne.setTimeOut("7200");
            litterHelperRespository.save(userOne);
            //将数据放入map然后转换成json返给前端
            Map config = new HashMap();
            config.put("appId", "wxdfe8e9a69851a406");
            config.put("timestamp", timestamp);
            config.put("nonceStr", nonceStr);
            config.put("signature", signature);
            config.put("ticket", jsapi_ticket);
            JSONObject jsonRes = JSONObject.fromObject(config);
            return jsonRes;
        } else {
            logger.info("直接加密");
            logger.info("JsapiTicket()" + userOne.getJsapiTicket());
            String signature = "";
            //jsapi签名(其他三个参数由前端传递过来,保持一致)
            String sign = "jsapi_ticket=" + userOne.getJsapiTicket() + "&noncestr="
                    + nonceStr + "&timestamp=" + timestamp
                    + "&url=" + url + "";
            try {
                //获取加密签名
                signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
                logger.info("signature" + signature);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //将数据放入map然后转换成json返给前端
            Map config = new HashMap();
            config.put("appId", "wxdfe8e9a69851a406");
            config.put("timestamp", timestamp);
            config.put("nonceStr", nonceStr);
            config.put("signature", signature);
            config.put("ticket", userOne.getJsapiTicket());
            JSONObject jsonRes = JSONObject.fromObject(config);
            return jsonRes;
        }
    }

}
