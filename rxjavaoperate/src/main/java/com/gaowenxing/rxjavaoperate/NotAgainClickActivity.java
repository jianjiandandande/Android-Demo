package com.gaowenxing.rxjavaoperate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observer;

public class NotAgainClickActivity extends AppCompatActivity {

    private long lastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_again_click);

        Button btn = (Button) this.findViewById(R.id.btn_click);


        //throttleFirst允许设置一个时间长度，之后它会发送固定时间长度内的第一个事件，隔过时间间隔后才会发送新的时间间隔内的第一个数据
        RxView.clicks(btn).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Observer<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                Log.d("", "onNext: "+"这是按钮点击");
            }
        });
    }

    public void not_again_click(View view) {

        long currTime = System.currentTimeMillis();

       real_click(currTime);

    }


    /**
     * 使用普通的方式来实现防止重复点击
     * @param currTime
     */
    private void real_click(long currTime){

        if (currTime - lastTime>500){

            //做正常的操作

        }
        lastTime = currTime;
    }
}
