package com.gaowenxing.rxjava2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchedulerActivity extends AppCompatActivity {


    private TextView mTextView;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        mTextView = (TextView) this.findViewById(R.id.text_scheduler);

    }

    public void btn_scheduler(View view) {


        /**
         *  在不指定线程的情况下，Observable与Observer都是在主线程中执行的
         *
         *  在光指定了subscribeOn的情况下，Observable与Observer都是在子线程中执行的
         *
         */


        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) throws Exception {



                //请求网络
                User user = api.getUserInfoWithPath(1).execute().body();//同步方式

                e.onNext(user);


            }
        }).subscribeOn(Schedulers.io())//在io线程中进行网络请求
          .observeOn(AndroidSchedulers.mainThread())//在主线程中更新UI
          .subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {

                //更新UI

                mTextView.setText(user.getUsername());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
