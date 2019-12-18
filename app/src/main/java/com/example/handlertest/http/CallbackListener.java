package com.example.handlertest.http;

import java.io.IOException;
import java.io.InputStream;

public interface CallbackListener {

    void onSuccess(InputStream inputStream) throws IOException;

    void onFailure();
}
