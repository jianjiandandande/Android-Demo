package com.example.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * 这种产生IBinder的方式，要求服务与客户端在相同的进程当中，因为在客户端获取Service的过程中存在类型转换
 */
public class BinderService extends Service {
    public BinderService() {

    }

    public class LocaLBinder extends Binder{

        public BinderService getService(){
            return BinderService.this;
        }

    }

    public final IBinder mIBinder = new LocaLBinder();
    @Override
    public IBinder onBind(Intent intent) {
       return mIBinder;
    }

    public String getMessage(){
        return "hello world";
    }
}
