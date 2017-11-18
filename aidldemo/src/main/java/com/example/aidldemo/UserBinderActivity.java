package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aidldemo.service.BinderService;

public class UserBinderActivity extends AppCompatActivity {

    private BinderService mService;
    private static final String TAG = "UserBinderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_binder);
    }

    public void bindService(View view) {

        bindService(new Intent(this, BinderService.class),mServiceConnection,BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Toast.makeText(UserBinderActivity.this, "connect", Toast.LENGTH_SHORT).show();

            BinderService.LocaLBinder binder = (BinderService.LocaLBinder) iBinder;
            mService = binder.getService();

            Log.d(TAG, "onServiceConnected: "+mService.getMessage());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(UserBinderActivity.this, "disConnect", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }
}
