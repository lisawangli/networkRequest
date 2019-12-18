package com.example.handlertest.http;

import com.example.handlertest.Message;

import java.util.concurrent.ThreadPoolExecutor;

public class NetUtil {

    public static<T,M> void sendJsonrequest(String url,T requestData,String method,Class<M> respose,IJsonDataListener listener){
        IHttpRequest httpRequest = new JsonHttprequest();
        CallbackListener callbackListener = new JsonCallbacklistener<>(respose,listener);
        HttpTask httpTask = new HttpTask(url,requestData,httpRequest,method,callbackListener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
