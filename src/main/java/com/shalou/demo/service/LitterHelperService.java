package com.shalou.demo.service;

import com.google.gson.Gson;
import com.shalou.demo.domain.LitterHelperUser;
import com.shalou.demo.repository.LitterHelperRespository;
import com.shalou.demo.utils.ResultUtil;
import com.shalou.demo.utils.SHA1;
import com.shalou.demo.utils.SendHttpRequest;
import com.shalou.demo.utils.XmlOrMapToggle;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.shalou.demo.utils.HttpClientUtils.postXML;
import static com.shalou.demo.utils.signUtils.createSign;

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

    //微信统一下单
    public Object wePay(String body,String device_info,String nonceStr,String out_trade_no,String total_fee,String spbill_create_ip,String timeStamp,String openid){

        //打印参数查看是否获取到
        logger.info("打印参数 >>>>>>>>>>>>>>>>>");
        logger.info("body >>>>" + body);
        logger.info("device_info >>>>" + device_info);
        logger.info("nonceStr >>>>" + nonceStr);
        logger.info("out_trade_no >>>>" + out_trade_no);
        logger.info("total_fee >>>>" + total_fee);
        logger.info("spbill_create_ip >>>>" + spbill_create_ip);
        logger.info("timeStamp >>>>" + timeStamp);
        //timeStamp二次签名需要(统一下单不需要)注意s大小写,前后端不一致
        //设置map数据
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        //公众账号ID
        parameters.put("appid", "wxdfe8e9a69851a406");
        //商品描述
        parameters.put("body", body);
        //设备号
        parameters.put("device_info", device_info);
        //商户号
        parameters.put("mch_id", "1488241012");
        //支付成功后的回调地址(post接口)
        parameters.put("notify_url", "http://dsx2016.s1.natapp.cc/shalou/litter/payRes");
        //随机字符串
        parameters.put("nonce_str", nonceStr);
        //openid(文档没写,但是加密记得时候签名需要,巨坑)
        parameters.put("openid", openid);
        //商户订单号
        parameters.put("out_trade_no", out_trade_no);
        //终端IP
        parameters.put("spbill_create_ip", spbill_create_ip);
        //标价金额
        parameters.put("total_fee", total_fee);
        //交易类型
        parameters.put("trade_type", "JSAPI");
        //签名类型(巨坑,一定传)
        parameters.put("sign_type", "MD5");

        //设置编码
        String characterEncoding = "UTF-8";
        //获取第一次签名(使用了封装的各种类)
        String mySign = createSign(characterEncoding, parameters);
        //第一次签名
        System.out.println("我 的签名是：" + mySign);

        //把以上的数据加上返回的第一次签名转为xml
        Map<String, String> mapSign = new HashMap<String, String>();
        mapSign.put("sign", mySign);
        //公众账号ID
        mapSign.put("appid", "wxdfe8e9a69851a406");
        //商品描述
        mapSign.put("body", body);
        //设备号
        mapSign.put("device_info", device_info);
        //商户号
        mapSign.put("mch_id", "1488241012");
        //支付成功后的回调地址(post接口)
        mapSign.put("notify_url", "http://dsx2016.s1.natapp.cc/shalou/litter/payRes");
        //随机字符串
        mapSign.put("nonce_str", nonceStr);
        //openid
        mapSign.put("openid", openid);
        //商户订单号
        mapSign.put("out_trade_no", out_trade_no);
        //终端IP
        mapSign.put("spbill_create_ip", spbill_create_ip);
        //标价金额
        mapSign.put("total_fee", total_fee);
        //交易类型
        mapSign.put("trade_type", "JSAPI");
        //签名类型
        mapSign.put("sign_type", "MD5");
        //转为xml
        String xml = XmlOrMapToggle.map2XmlString(mapSign);
        logger.info("打印参数xml >>>>>>>>>>>>>>>>>" + xml);

        //微信公众号统一下单地址
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        //将数据转为字符串XML
        try {
            //向微信发起统一下单请求
            String responseContent = postXML(url, xml);
            //获取到结果
            System.out.println("商户返回:" + responseContent);
            //解析微信xml为map
            Map map2 = XmlOrMapToggle.readStringXmlOut(responseContent);
            //将map转为json
            JSONObject jsonRes = JSONObject.fromObject(map2);
            //打印结果
            System.out.println("map2：" + map2);
            //取出json中的某个key值
            String prepay_id = jsonRes.getString("prepay_id");
            System.out.println("获取二次签名需要的参数：" + prepay_id);

            //设置map数据(用于二次签名)
            SortedMap<Object, Object> signAgian = new TreeMap<Object, Object>();
            //设置发起支付必填数据(按照字母排列)
            //appId(一定要注意大小写啊啊啊啊)所有的字段都要注意
            signAgian.put("appId", "wxdfe8e9a69851a406");
            //nonceStr
            signAgian.put("nonceStr", nonceStr);
            //package
            signAgian.put("package", "prepay_id=" + prepay_id);
            //MD5
            //signAgian.put("sign_type", "MD5");
            //MD5(注意字段名)
            signAgian.put("signType", "MD5");
            //timeStamp
            signAgian.put("timeStamp", timeStamp);

            //获取二次签名(使用了封装的各种类)
            String mySignTwo = createSign(characterEncoding, signAgian);
            //签名
            System.out.println(" mySignTwo的签名是：" + mySignTwo);

            Map<String, String> resMap = new HashMap<String, String>();
            resMap.put("paySign", mySignTwo);
            resMap.put("package", "prepay_id=" + prepay_id);

            return resMap;


            //返回前端支付需要的参数
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "出错啦";
    }


    //微信支付回调需要的工具函数
    /**
     * 插入XML标签
     *
     * @param sb
     * @param Key
     * @param value
     * @return
     */
    public static StringBuilder setXmlKV(StringBuilder sb, String Key, String value) {
        sb.append("<");
        sb.append(Key);
        sb.append(">");

        sb.append(value);

        sb.append("</");
        sb.append(Key);
        sb.append(">");

        return sb;
    }

    //微信支付回调需要的工具函数
    /**
     * 解析XML 获得名称为para的参数值
     *
     * @param xml
     * @param para
     * @return
     */
    public static String getXmlPara(String xml, String para) {
        int start = xml.indexOf("<" + para + ">");
        int end = xml.indexOf("</" + para + ">");

        if (start < 0 && end < 0) {
            return null;
        }
        return xml.substring(start + ("<" + para + ">").length(), end).replace("<![CDATA[", "").replace("]]>", "");
    }

    //微信支付后回调
    public String payRes(HttpServletRequest request){
        //初始化变量
        String inputLine;
        String notifyXml = "";
        String resXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notifyXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            //logger.debug("xml获取失败：" + e);
            e.printStackTrace();
        }


        System.out.println("接收到的xml：" + notifyXml);

        //微信发来的数据(用于验证签名等)
        String appid = getXmlPara(notifyXml, "appid");
        String bank_type = getXmlPara(notifyXml, "bank_type");
        String cash_fee = getXmlPara(notifyXml, "cash_fee");
        String fee_type = getXmlPara(notifyXml, "fee_type");
        String is_subscribe = getXmlPara(notifyXml, "is_subscribe");
        String mch_id = getXmlPara(notifyXml, "mch_id");
        String nonce_str = getXmlPara(notifyXml, "nonce_str");
        String openid = getXmlPara(notifyXml, "openid");
        String out_trade_no = getXmlPara(notifyXml, "out_trade_no");
        String result_code = getXmlPara(notifyXml, "result_code");
        String return_code = getXmlPara(notifyXml, "return_code");
        String sign = getXmlPara(notifyXml, "sign");
        String time_end = getXmlPara(notifyXml, "time_end");
        String total_fee = getXmlPara(notifyXml, "total_fee");
        String trade_type = getXmlPara(notifyXml, "trade_type");
        String transaction_id = getXmlPara(notifyXml, "transaction_id");

        //todo
        /*1.首先拿到本地的sign
        * 2.其次把微信的数据再次sign
        * 3.把两个签名比对校验
        * 4.如果对的话就是支付成功并且通知微信,然后改变用户的订单状态
        * 5.如果不对就是支付失败*/

        System.out.println("支付成功....");
        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        return resXml;
    }

}
