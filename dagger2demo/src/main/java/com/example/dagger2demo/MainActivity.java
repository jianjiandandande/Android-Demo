package com.example.dagger2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerUserComponent.create().inject(this);//如果提供需求的Module在实例化的时候，不需要参数，则使用create(),如果Module需要参数，则使用builder()方法
        mApiService.register();

    }
}
