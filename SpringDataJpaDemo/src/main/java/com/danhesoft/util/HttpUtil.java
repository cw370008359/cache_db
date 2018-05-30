package com.danhesoft.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by caowei on 2018/5/9.
 */
public class HttpUtil {

    private static final CloseableHttpClient httpclient;

    static{
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();
        httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * Post请求
     * @param url
     * @param params
     * @return
     */
    public String postData(String url, Map<String, Object> params){
        //finally close all
        try{
           //准备要发送的数据
            List<NameValuePair> pairs = null;
            if(params!=null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(String key: params.keySet()){
                    pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
            }
            if(pairs!=null && pairs.size()>0){
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
                CloseableHttpResponse response = httpclient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200){
                    httpPost.abort();
                    throw new RuntimeException("HttpClient,error status code :" + statusCode);
                }
                HttpEntity entity = response.getEntity();
                String result = null;
                if (entity != null) {
                    result = EntityUtils.toString(entity, "utf-8");
                    EntityUtils.consume(entity);
                    response.close();
                    return result;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String putData(String url, Map<String, Object> params){
        try{
            //准备要发送的数据
            if(params!=null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(String key: params.keySet()){
                    pairs.add(new BasicNameValuePair(key, URLEncoder.encode(params.get(key).toString())));
                }
                url +="?"+EntityUtils.toString(new UrlEncodedFormEntity(pairs), "utf-8");
                //httpPut.setHeader("Content-type", "application/json");
            }
            if(!StringUtils.isEmpty(url)){
                HttpPut httpPut = new HttpPut(url);
                CloseableHttpResponse response = httpclient.execute(httpPut);
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200){
                    httpPut.abort();
                    throw new RuntimeException("HttpClient,error status code :" + statusCode);
                }
                HttpEntity entity = response.getEntity();
                String result = null;
                if (entity != null) {
                    result = EntityUtils.toString(entity, "utf-8");
                    EntityUtils.consume(entity);
                    response.close();
                    return result;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


}
