package com.gupiao.service;

import com.gupiao.enums.ApiUrlPath;
import com.gupiao.util.StaticValue;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供http服务类
 */
public class HttpService {

    /**
     * 从指定路径获取数据
     * @param url
     * @param params
     * @return
     */
    public static String getDataFromUrl(ApiUrlPath url, Map<String ,String> params) throws IOException {
        String urlRes = url.getPath();
        if(null != params && params.size() > 0){
            for (Map.Entry<String,String> entry:params.entrySet()) {
                urlRes = urlRes.replace(entry.getKey(),entry.getValue());
            }
        }
        return doGet(StaticValue.DATA_URL_PATH + urlRes);
    }

    public static String doGet(String url) throws java.io.IOException {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        request.addHeader("User-Agent", "Mozilla/5.0");
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public static String doPost(String url) throws java.io.IOException {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", "Mozilla/5.0");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

}
