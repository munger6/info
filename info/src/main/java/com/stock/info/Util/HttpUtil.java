package com.stock.info.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.stock.info.constant.PublicConstant;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger("WARN_FILE");


    public static final String urlLinke = "http://api.waditu.com";

    /**
     * doGet请求连接
     * @param url
     * @return
     */
    public static String doGet(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * doPost请求链接
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static String doPost(String url, Map<String, Object> paramMap, Map<String,String> headerMap){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        if(MapUtils.isNotEmpty(headerMap)){
            for (String key :headerMap.keySet()){
                httpPost.addHeader(key, headerMap.get(key));
            }
        }else{
            //默认使用raw的请求
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        }

        try {
            // 封装post请求参数
            if (MapUtils.isNotEmpty(paramMap)) {
//                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//                // 通过map集成entrySet方法获取entity
//                Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
//                // 循环遍历，获取迭代器
//                Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
//                while (iterator.hasNext()) {
//                    Map.Entry<String, Object> mapEntry = iterator.next();
//                    nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
//                }
//                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

                // 为httpPost设置封装好的raw请求参数
                httpPost.setEntity(new StringEntity(JSON.toJSONString(paramMap), "UTF-8"));
            }

            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
           logger.error("HTTP请求参数设置异常",e);
        } catch (IOException e) {
            logger.error("HTTP请求IO异常",e);
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 初始化请求参数（查询全面字段返回）
     * @param apiName  请求apiName
     * @return
     */
    public static Map<String,Object> initParam(String apiName, String token){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("api_name",apiName);
        paramMap.put("token", token);
        paramMap.put("fields","");
        return paramMap;
    }


    /***
     * 加工查询回来数据为Map<key,value>的一条数据形式
     * @param data
     * @param array
     * @return
     */
    public static Map<String, Object> processDataToMap(JSONArray data, JSONArray array) {
        Map<String, Object> dataMap = new HashMap<>();
        for (int j = 0; j < data.size(); j++) {
            if(data.get(j) instanceof Number){
                dataMap.put(array.getString(j),data.getBigDecimal(j));
            }else{
                logger.warn("写入值为String的字段为" + array.getString(j) + "返回值为" + data.getString(j));
                dataMap.put(array.getString(j),data.getString(j));
            }
        }
        return dataMap;
    }
}
