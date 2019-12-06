package com.example.weathershow.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    //向服务器发送获取天气信息的请求
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
