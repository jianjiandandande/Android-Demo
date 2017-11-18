package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MessagerActivity extends AppCompatActivity {

    public static final int MSG_SAY_HELLO = 1;
    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);
    }

    public void MessagerService(View view) {

        Message message = new Message();
        message.what = MSG_SAY_HELLO;
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("com.Vincent.messenger");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage("com.example.aidldemo");

        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            messenger = new Messenger(iBinder);
            Toast.makeText(MessagerActivity.this, "connect", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(MessagerActivity.this, "disconnect", Toast.LENGTH_SHORT).show();
        }
    };
}
