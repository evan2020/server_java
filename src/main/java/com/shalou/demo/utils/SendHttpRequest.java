package com.shalou.demo.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import net.sf.json.JSONObject;

public class SendHttpRequest {
    /**
     * @param url
     * @param jsonParam
     * @return
     */
    public static JSONObject sendGet(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpGet method = new HttpGet(url);
        try {
            CloseableHttpResponse result = httpclient.execute(method);
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    str = EntityUtils.toString(result.getEntity());
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    System.out.println("get请求提交失败:" + url);
                }
            }
        } catch (IOException e) {
            System.out.println("get请求提交失败:" + url);
        }
        return jsonResult;
    }
}
