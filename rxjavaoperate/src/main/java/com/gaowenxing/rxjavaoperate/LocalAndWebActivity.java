package com.gaowenxing.rxjavaoperate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocalAndWebActivity extends AppCompatActivity {

    private Api api;

    private static final String TAG = "LocalAndWebActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_and_web);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);

    }

    public void click(View view) {

        Observable.merge(getDataFromLocal(),getDataFromNetWork())
                .subscribe(new Subscriber<List<Course>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Course> courses) {

                        for (Course course : courses) {

                            Log.d(TAG, "onNext: coursename = "+course.getName());

                        }

                    }
                });

    }


    /**
     * 从本地获取数据
     * @return
     */
    private Observable<List<Course>> getDataFromLocal(){

        List<Course> list = new ArrayList<>();

        list.add(new Course("高数"));

        list.add(new Course("英语"));

        return Observable.just(list);
    }

    /**
     * 从网络获取数据
     * @return
     */
    private Observable<List<Course>> getDataFromNetWork(){

        return api.getCourse().subscribeOn(Schedulers.io()) ;
    }
}
