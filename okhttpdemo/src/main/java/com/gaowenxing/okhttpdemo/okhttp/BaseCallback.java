package com.gaowenxing.okhttpdemo.okhttp;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by lx on 2017/8/13.
 */

public interface BaseCallback<T> {

    void onError(int code);

    void onFailure(Call call, IOException e);

    void onSuccess(T t);
}
