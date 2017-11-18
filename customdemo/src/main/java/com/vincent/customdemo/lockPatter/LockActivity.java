package com.vincent.customdemo.lockPatter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class LockActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                PasswordFragment.newInstance(PasswordFragment.TYPE_SETTING)).commit();
    }


}
