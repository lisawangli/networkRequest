package com.example.handlertest.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallbacklistener<T> implements CallbackListener {
    Class<T> responseClass;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private IJsonDataListener jsonDataListener;
    public JsonCallbacklistener(Class<T> responseClass,IJsonDataListener listener){
        this.responseClass = responseClass;
        this.jsonDataListener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) throws IOException {

        String response = getContent(inputStream);
        final T clazz = JSON.parseObject(response,responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                jsonDataListener.onSuccess(clazz);
            }
        });

    }

    private String getContent(InputStream inputStream)  {
        String content = null;
        try {
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                sb.append(line);
            }
            content = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content;
    }

    @Override
    public void onFailure() {

    }
}
