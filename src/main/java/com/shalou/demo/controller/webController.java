package com.shalou.demo.controller;

import com.shalou.demo.utils.XmlOrMapToggle;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class webController {



    //获取ip地址
    @GetMapping(value = "/getIp")
    @ResponseBody
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }

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
    @GetMapping(value = "/txt")
    public String txt() {
        return "/pay";
    }



}
