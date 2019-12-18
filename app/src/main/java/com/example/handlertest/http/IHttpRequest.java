package com.example.handlertest.http;

public interface IHttpRequest {

    void setUrl(String url);

    void setData(byte[] data);

    void requestMethod(String method);

    void setListener(CallbackListener callbackListener);

    void execute();
}
