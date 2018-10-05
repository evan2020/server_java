package com.shalou.demo.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class signUtils {

    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + "huxiaoshuang"); // 此处填写商户的key(下次记得比对商户key)
        System.out.println("字符串:" + sb.toString());
        String sign = UtilsMD5.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

}
