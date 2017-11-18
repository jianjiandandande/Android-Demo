package com.gaowenxing.rxjavaoperate;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

public class DaoJiShiActivity extends AppCompatActivity {

    private static final String TAG = "DaoJiShiActivity";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao_ji_shi);

        mButton = (Button) this.findViewById(R.id.btn_click_daojishi);
    }

    public void click(View view) {

        final int count = 10;


        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(count+1)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return count - aLong;//将数字倒序
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //在开始倒计时之前将按钮设置为不可点击
                        mButton.setEnabled(false);
                        mButton.setTextColor(Color.BLACK);
                    }
                })
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                        Log.d(TAG, "onCompleted: ");

                        //在完成倒计时之前将按钮设置为可点击
                        mButton.setEnabled(true);
                        mButton.setText("重新发送验证码");

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long num) {
                        Log.d(TAG, "onNext: "+num);
                        mButton.setText("剩余 "+num+" 秒");
                    }
                });


    }
}
