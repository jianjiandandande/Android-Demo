package com.example.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Vincent on 2017/11/18.
 */

public class StartService extends Service {


    private static final String TAG = "ServiceDome";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        //只在service创建的时候调用一次，可以在此进行一些一次性的初始化操作
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        //Toast.makeText(this, "startService", Toast.LENGTH_SHORT).show();
        //当其他组件调用startService()方法时，此方法将会被调用
        //在这里进行这个service主要的操作
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        //当其他组件调用bindService()方法时，此方法将会被调用
        //如果不想让这个service被绑定，在此返回null即可
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        //service调用的最后一个方法
        //在此进行资源的回收
        super.onDestroy();
    }
}
