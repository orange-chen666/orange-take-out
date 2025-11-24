package com.orange.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
//使用try-with-resources 保证资源一定被关闭
    static final  int TIMEOUT_MSEC = 5 * 1000;
    public static String doGet(String url , Map<String,String> paramMap){
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
             URIBuilder uriBuilder = new URIBuilder(url);
             if (paramMap != null) {
                 for (String key : paramMap.keySet()) {
                     uriBuilder.addParameter(key,paramMap.get(key));
                 }
             }
             URI uri = uriBuilder.build();
             //创建Get请求
            HttpGet httpGet = new HttpGet(uri);
            //发送请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
               result  =  EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String doPost(String url , Map<String,String> paramMap) {
        String resultString = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> paramList = new ArrayList();
            if (paramMap != null){
                for (Map.Entry<String,String> param : paramMap.entrySet()) {
                    paramList.add(new BasicNameValuePair(param.getKey(),param.getValue()));
                    //模拟表单
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                    httpPost.setEntity(entity);

                }
            }
            httpPost.setConfig(builderRequestConfig());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(),"utf-8");
           // EntityUtils.consume(response.getEntity()); // 确保实体被完全消费，我还没使用连接池
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 这个方法先不搞
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static String doPost4Json(String url, Map<String, String> paramMap) throws IOException {

        return null;
    }
    private static RequestConfig builderRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_MSEC)
                .setConnectionRequestTimeout(TIMEOUT_MSEC)
                .setSocketTimeout(TIMEOUT_MSEC).build();
    }
}
