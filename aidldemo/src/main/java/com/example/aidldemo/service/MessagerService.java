package com.example.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;


/**
 *  这种产生IBinder的方式，实现了跨进程通信
 *  它的内部采用handler的方式，通过发送消息的方式进行进程的通信
 *  缺点：如果项目中有并发处理问题的需求，或者会有大量的并发请求，这个时候Messenger就不适用了——它的特性让它只能串行的解决请求。
 */
public class MessagerService extends Service {

    public static final int MSG_SAY_HELLO = 1;

    public MessagerService() {
    }

    public class MessagerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello! Messager", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessagerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
