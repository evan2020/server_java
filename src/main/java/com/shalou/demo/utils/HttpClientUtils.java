package com.shalou.demo.utils;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.thymeleaf.util.MapUtils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;

    static {
        try {
            builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, sslsf).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * httpClient post请求     *     * @param url    请求url     * @param header 头部信息     * @param param  请求参数 form提交适用     * @param entity 请求实体 json/xml提交适用     * @return 可能为空 需要处理     * @throws Exception
     */
    public static String post(String url, Map<String, String> header, Map<String, String> param, StringEntity entity) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            if (MapUtils.isEmpty(header)) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if (MapUtils.isEmpty(param)) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            if (entity != null) {
                httpPost.addHeader("Content-Type", "text/xml");
                httpPost.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("状态码：" + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                readHttpResponse(httpResponse);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }

    public static String postXML(String url, String xml) {
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
            client = HttpClients.createDefault();
            StringEntity entityParams = new StringEntity(xml, "UTF-8");
            httpPost.setEntity(entityParams);
            client = HttpClients.createDefault();
            resp = client.execute(httpPost);
            String resultMsg = EntityUtils.toString(resp.getEntity(), "UTF-8");
            return resultMsg;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
                if (resp != null) {
                    resp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm).setConnectionManagerShared(true).build();
        return httpClient;
    }

    public static String readHttpResponse(HttpResponse httpResponse) throws ParseException, IOException {
        StringBuilder builder = new StringBuilder();
        HttpEntity entity = httpResponse.getEntity();
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:" + responseString.replace("\r\n", ""));
        }
        return builder.toString();
    }
}