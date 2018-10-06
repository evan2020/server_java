package com.shalou.demo.controller;

import com.shalou.demo.utils.SHA1;
import com.shalou.demo.utils.SendHttpRequest;
import com.shalou.demo.utils.XmlOrMapToggle;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.shalou.demo.utils.HttpClientUtils.postXML;
import static com.shalou.demo.utils.signUtils.createSign;

@Controller
public class webController {
    private String timestamp = "1538701719"; //需要保留10位(转成秒)
    private String noncestr = "Wm3WZYTPz0wzccnW";
    private String jsapi_ticket = "kgt8ON7yVITDhtdwci0qedtomD0dwCm-br9XYkQsyT5_VltiIZUNMZab9WUr3kgMl-dX7MKv4Qqmf8ZYZeQiIQ";
    private String apiurl = "http://dsx2016.s1.natapp.cc/shalou/auth";
    private String token = "14_4PdaD9rmOtNqMAg3ayXliYq2PGCluXkQiUjaBHLw_WrbaZw2kEvX6z8QbiblbkugJTRWlFKY7Tfz1qabH1GrP6LE5SCyuMNqc_fPZ0jmAUuhq2T_6PD83ULK_4Yy54VYZrzHLMImYsGHpMQ5DGXjADAWZQ";
    private String openid = "opBQtwcFrxY89xFPrAKwzXLhmop4";
    private String signature = "bd9b8185b8464b4b7cb7ae989659ef048becd529";

    @GetMapping(value = "/xmlToggle")
    @ResponseBody
    public Object xml() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("test", "123");
        map.put("try", "456");
        String xml = XmlOrMapToggle.map2XmlString(map);
        System.out.println("xml：" + xml);

        String sXml = "<xml>\n" +
                "<appid>wxdfe8e9a69851a406</appid>\n" +
                "<body>test1</body>\n" +
                "<device_info>1001</device_info>\n" +
                "<mch_id>1488241012</mch_id>\n" +
                "<notify_url>http://dsx2016.s1.natapp.cc/shalou/auth</notify_url>\n" +
                "<nonce_str>ibuaiVcKdpRxkhJA</nonce_str>\n" +
                "<out_trade_no>20150806125346211</out_trade_no>\n" +
                "<openid>opBQtwcFrxY89xFPrAKwzXLhmop4</openid>\n" +
                "<spbill_create_ip>113.89.97.6</spbill_create_ip>\n" +
                "<total_fee>3</total_fee>\n" +
                "<trade_type>JSAPI</trade_type>\n" +
                "<sign>A18E3E74F8E19ED73A468398DBD30A83</sign>\n" +
                "</xml>";
        //初始化map
        Map map2;
        //解析xml为map
        map2 = XmlOrMapToggle.readStringXmlOut(sXml);
        //将map转为json
        JSONObject jsonRes = JSONObject.fromObject(map2);
        //打印结果
        System.out.println("map2：" + map2);
        //取出json中的某个key值
        String test3 = jsonRes.getString("sign");
        //返回数据
        return test3;
    }

    //测试接口
    //登录回调或者jsapi接口
    @GetMapping(value = "/auth")
    @ResponseBody
    public Object test(@RequestParam("code") String code) {
        System.out.println("code：" + code);
        //获取登录授权的token(与jsapi token不一样)
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxdfe8e9a69851a406&secret=16007b600d84658cbbe894488efb9940&code=" + code + "&grant_type=authorization_code";
        //其中一种请求方式
        // RestTemplate restTemplate = new RestTemplate();
        //获取openid
        //String res = restTemplate.getForObject(url, String.class);
        //另外一种请求方式
        JSONObject json = SendHttpRequest.sendGet(url);
        String login_openid = json.getString("openid");
        String login_access_token = json.getString("access_token");
        System.out.println("login_access_token：" + login_access_token);
        String login_lang = "zh_CN";
        String userInfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + login_access_token + "&openid=" + login_openid + "&lang=" + login_lang;
        JSONObject userInfo = SendHttpRequest.sendGet(userInfo_url);
        System.out.println("userInfo：" + userInfo);
        // 编码后的json
        String userInfoBase = "";

        try {
            userInfoBase = new String(userInfo.getString("nickname").getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("userInfoBase：" + userInfoBase);
        return userInfoBase;
        //return "/pay";
    }

    //微信jsapi签名
    @GetMapping(value = "/signature")
    @ResponseBody
    public String signature() {
        //jsapi签名
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                + noncestr + "&timestamp=" + timestamp
                + "&url=" + apiurl + "";
        try {
            signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("signature：" + signature);
        return signature;
    }


    //获取jsapi的config(access_token和jsapi_ticket)
    @GetMapping(value = "/jsapi")
    @ResponseBody
    public String jsapi() {
        //获取jsapi中的token( 需要在公众号中设置ip白名单才能刷新token)
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxdfe8e9a69851a406&secret=16007b600d84658cbbe894488efb9940";
        JSONObject json = SendHttpRequest.sendGet(tokenUrl);
        System.out.println("json：" + json);
        String access_token = json.getString("access_token");
        //获取ticket_url
        String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        System.out.println("ticket_url：" + ticket_url);
        String jsapi_ticket = SendHttpRequest.sendGet(ticket_url).getString("ticket");
        System.out.println("jsapi_ticket：" + jsapi_ticket);
        return "jsapi";
    }

    //测试接口
    @GetMapping(value = "/txt")
    public String txt() {
        return "/pay";
    }

    //验证微信安全域名和回调域名
    @GetMapping(value = "/MP_verify_bIYfTjRREsRFUbaE.txt")
    public String txtBook() {
        return "/MP_verify_bIYfTjRREsRFUbaE.txt";
    }

    //h5微信支付二次签名
    @GetMapping(value = "/paysign")
    @ResponseBody
    public String paysign() {
        //设置map数据
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        //设置发起支付必填数据(按照字母排列)
        //appid
        parameters.put("appid", "wxdfe8e9a69851a406");
        //nonceStr
        parameters.put("nonceStr", "Hd5xntLGwf3b3wpZ");
        //package
        parameters.put("package", "prepay_id=wx05103923096711f8adf0c1420563542728");
        //MD5
        parameters.put("signType", "MD5");
        //timeStamp
        parameters.put("timeStamp", "1538701719");

        //设置编码
        String characterEncoding = "UTF-8";
        //获取签名(使用了封装的各种类)
        String mySign = createSign(characterEncoding, parameters);
        //签名
        System.out.println("我 的签名是：" + mySign);

        return "paysign";
    }

    //设置微信支付
    @GetMapping(value = "/md5")
    @ResponseBody
    public String md5() {

        //设置map数据
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        //设置发起支付必填数据(按照字母排列)
        //公众账号ID
        parameters.put("appid", "wxdfe8e9a69851a406");
        //商品描述
        parameters.put("body", "test1");
        //设备号
        parameters.put("device_info", "1001");
        //商户号
        parameters.put("mch_id", "1488241012");
        //通知地址(必须后台设置)
        parameters.put("notify_url", "http://dsx2016.s1.natapp.cc/shalou/auth");
        //随机字符串
        parameters.put("nonce_str", "ibuaiVcKdpRxkhJA");
        //openid
        parameters.put("openid", "opBQtwcFrxY89xFPrAKwzXLhmop4");
        //商户订单号
        parameters.put("out_trade_no", "20150806125346211");
        //终端IP
        parameters.put("spbill_create_ip", "113.89.97.6");
        //标价金额
        parameters.put("total_fee", "3");
        //交易类型
        parameters.put("trade_type", "JSAPI");

        //设置编码
        String characterEncoding = "UTF-8";
        //获取签名(使用了封装的各种类)
        String mySign = createSign(characterEncoding, parameters);
        //签名
        System.out.println("我 的签名是：" + mySign);


        return "md5";
    }

    //微信支付统一下单
    @GetMapping(value = "/payOder")
    @ResponseBody
    public String payOder() {
        //微信公众号统一下单地址
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        //将数据转为字符串XML
        try {
            String responseContent = postXML(url, "<xml>\n" +
                    "<appid>wxdfe8e9a69851a406</appid>\n" +
                    "<body>test1</body>\n" +
                    "<device_info>1001</device_info>\n" +
                    "<mch_id>1488241012</mch_id>\n" +
                    "<notify_url>http://dsx2016.s1.natapp.cc/shalou/auth</notify_url>\n" +
                    "<nonce_str>ibuaiVcKdpRxkhJA</nonce_str>\n" +
                    "<out_trade_no>20150806125346211</out_trade_no>\n" +
                    "<openid>opBQtwcFrxY89xFPrAKwzXLhmop4</openid>\n" +
                    "<spbill_create_ip>113.89.97.6</spbill_create_ip>\n" +
                    "<total_fee>3</total_fee>\n" +
                    "<trade_type>JSAPI</trade_type>\n" +
                    "<sign>A18E3E74F8E19ED73A468398DBD30A83</sign>\n" +
                    "</xml>");
            System.out.println("商户返回:" + responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "payOder";
    }

}
