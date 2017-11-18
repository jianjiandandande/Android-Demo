package com.example.aidldemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Vincent on 2017/11/18.
 */


/**
 * IntentService相比较Service的优点:
 * 1.创建默认的工作线程，用于在应用的主线程外执行传递给 onStartCommand() 的所有 Intent
 * 2.创建工作队列，用于将一个 Intent 逐一传递给 onHandleIntent() 实现，这样的话就永远不必担心多线程问题了
 * 3.在处理完所有启动请求后停止服务，从此妈妈再也不用担心我忘记调用 stopSelf() 了
 * 4.提供 onBind() 的默认实现（返回 null）
 * 5.提供 onStartCommand() 的默认实现，可将 Intent 依次发送到工作队列和 onHandleIntent() 实现
 *
 * 注意:
 * (如果你不要求这个service要同时处理多个请求，可以直接使用IntentService来代替Service)
 * 相比上面的继承service实现，这个确实要简单许多。但是要注意的是，如果需要重写其他的方法，比如onDestroy()方法，
 * 一定不要删掉它的超类实现！因为它的超类实现里面也许包括了对工作线程还有工作队列的初始化以及销毁等操作，如果没有
 * 了的话很容易出问题。
 */
public class MyIntentService extends IntentService {
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
