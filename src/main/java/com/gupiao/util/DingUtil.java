package com.gupiao.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DingUtil {

    public static void sendDingTalk(String sendMsg) {

        Map content = new HashMap<>();
        content.put("content","[GP]通知:\n" + sendMsg);

        Map msg = new HashMap<>();
        msg.put("msgtype","text");
        msg.put("text",content);
        String json_str = new Gson().toJson(msg);

        log.info("dingdingmsg:{}",json_str);

        try {
            URL url = new URL(StaticValue.DING_DING_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write(json_str.getBytes("UTF-8"));
            os.close();
            InputStream is = connection.getInputStream();
            is.close();
            connection.disconnect();
        } catch (IOException e) {
            log.error("发送钉钉消息失败",e);
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        DingUtil.sendDingTalk("测试 \n from:[GP]");
    }

}
