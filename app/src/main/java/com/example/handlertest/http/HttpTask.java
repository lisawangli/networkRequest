package com.example.handlertest.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class HttpTask<T> implements Runnable {

    private IHttpRequest iHttpRequest;
    public HttpTask(String url,T requestData,IHttpRequest httpRequest,String method,CallbackListener listener){
        iHttpRequest = httpRequest;
        httpRequest.setUrl(url);
        httpRequest.requestMethod(method);
        httpRequest.setListener(listener);
        String content = JSON.toJSONString(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        iHttpRequest.execute();
    }
}
