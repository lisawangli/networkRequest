package com.example.handlertest.http;

public interface IJsonDataListener<T> {

    void onSuccess(T t);

    void onFailure();
}
