package com.example.handlertest.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonHttprequest implements IHttpRequest {
    private String url;
    private byte[] data;
    private CallbackListener callbackListener;
    private String method;
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void requestMethod(String method) {
        this.method = method;
    }

    @Override
    public void setListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    @Override
    public void execute() {
        if (TextUtils.equals(method,"GET")) {
            sendGet();
        }else{
            sendPost();
        }

    }

    /**
     　　　* Post服务请求
     *
     * @return
     */
     void sendPost(){

        try {
            //建立连接
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //设置连接属性
            connection.setDoOutput(true); //使用URL连接进行输出
            connection.setDoInput(true); //使用URL连接进行输入
            connection.setUseCaches(false); //忽略缓存
            connection.setRequestMethod("POST"); //设置URL请求方法

            connection.setRequestProperty("Content-length", "" + data.length);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            connection.setRequestProperty("Charset", "UTF-8");

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            //建立输出流,并写入数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data);
            outputStream.close();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                callbackListener.onSuccess(connection.getInputStream());

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Get服务请求
     *
     * @return
     */
     String sendGet(){
        try{
            //建立连接
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            connection.connect();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                callbackListener.onSuccess(connection.getInputStream());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
