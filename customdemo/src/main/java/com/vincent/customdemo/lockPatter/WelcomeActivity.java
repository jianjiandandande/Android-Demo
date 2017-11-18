package com.vincent.customdemo.lockPatter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.vincent.customdemo.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);

                String password = sp.getString("password","");
                //没有设置密码
                if (TextUtils.isEmpty(password)){
                    startActivity(new Intent(WelcomeActivity.this,LockActivity.class));
                    finish();
                //密码解锁
                }else{
                    getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                            PasswordFragment.newInstance(PasswordFragment.TYPE_CHECK)).commit();
                }

            }
        },1000);

    }
}
