package com.gaowenxing.okhttpdemo.okhttp;

/**
 * Created by lx on 2017/8/13.
 */

public interface ProgressListener {


    void onProgress(int progress);

    void onDone(long totalSize);

}
