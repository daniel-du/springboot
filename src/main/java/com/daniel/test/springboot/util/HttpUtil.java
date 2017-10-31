package com.daniel.test.springboot.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>http请求工具</p>
 *
 * @author Daniel_Du
 * @since 2017/10/31 11:40
 */
public class HttpUtil {
    private static int SocketTimeout = 60000;// 3秒
    private static int ConnectTimeout = 60000;
    private static int connectionRequestTimeout = 60000;

    /**
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static String getUrl(String url, Map<String, String> paramMap){
        String param = "";
        if(!paramMap.isEmpty()){
            List<NameValuePair> formparams = setHttpParams(paramMap);
            param = URLEncodedUtils.format(formparams, "UTF-8");
        }
        String result = getUrl(url, param);
        System.out.println("result: " + result);
        return result;
    }

    /**
     *
     * @param url
     * @param params
     * @return
     */
    public static String getUrl(String url, String params){
        String result = "";
        if(StringUtils.isBlank(url)){
            return result;
        }
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet();
        if(StringUtils.isNotBlank(params)){
            httpGet.setURI(URI.create(url + "?" + params));
        } else {
            httpGet.setURI(URI.create(url));
        }
        try {
            HttpResponse httpResponse= httpclient.execute(httpGet);
            result = getHttpEntityContent(httpResponse);
            httpGet.abort();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送报文
     *
     * @param url
     * @param param
     * @return
     */
    public static String postUrl(String url, Map<String, String> param) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SocketTimeout)
                .setConnectTimeout(ConnectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build();// 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        String httpEntityContent = "";
        if (!param.isEmpty()) {
            try {
                List<NameValuePair> formparams = setHttpParams(param);
                UrlEncodedFormEntity paras = new UrlEncodedFormEntity(formparams, "UTF-8");
                httpPost.setEntity(paras);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            HttpResponse response = httpclient.execute(httpPost);
            httpEntityContent = getHttpEntityContent(response);
            httpPost.abort();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpEntityContent;
    }

    /**
     * 设置请求参数
     *
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    /**
     * 获得响应HTTP实体内容
     *
     * @param response
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) {
        String result = "";
        HttpEntity entity = response.getEntity();
        if(entity == null){
            return result;
        }
        try {
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得响应HTTP实体内容
     *
     * @param response
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private static String getHttpEntityResult(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inputStream = null;
            BufferedReader reader = null;
            try{
                inputStream = entity.getContent();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } finally {
                if(reader != null){
                    reader.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            }
        }
        return "";
    }
}
