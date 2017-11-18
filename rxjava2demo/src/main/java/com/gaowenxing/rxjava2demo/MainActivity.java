package com.gaowenxing.rxjava2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_get(View view) {

        Observable<String> observable = getObservable();


        /**
         * 普通写法
         */
//        Observer<String> observer = getObserver();
//
//        observable.subscribe(observer);

        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                //类似于observer中的onNext
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //类似于observer中的onError
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //类似于observer中的onComplete
            }
        });
    }

    /**
     * 获取被观察者
     * @return
     */
    private Observable<String> getObservable(){
        
        /*
        普通创建方式
         return Observable.create(new ObservableOnSubscribe<String>() {
            *//**
             * ObservableEmitter是一个发射器
             * 
             * onComplete 与 onError 只能存在一个，如果都调用了，前面的哪一个生效
             * 
             * @param e
             * @throws Exception
             *//*
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                
                e.onNext("大保健");//它会去调用observer中的onNext方法
                e.onNext("学习");
                e.onNext("看电影");
                e.onComplete();//它会去调用observer中的onComplete方法
                
                
            }
        });*/

             /*
             * just方式
             *return Observable.just("大保健","看电影");
             *
             */

             /*
             * fromArray方式
             * return Observable.fromArray("大保健","看电影");
             */

             return null;
    }

    /**
     * 获取观察者
     * @return
     */
     private Observer<String> getObserver(){
         
         Observer<String> observer = new Observer<String>() {

             /**
              * Disposable--一次性，可以停止订阅关系
              * @param d
              */

             @Override
             public void onSubscribe(Disposable d) {


                 //在接受数据之前调用
                 Log.i(TAG, "onSubscribe: ");
             }

             @Override
             public void onNext(String value) {
                 Log.i(TAG, "onNext: ");
             }

             @Override
             public void onError(Throwable e) {
                 Log.i(TAG, "onError: ");
             }

             @Override
             public void onComplete() {
                 Log.i(TAG, "onComplete: ");
             }
         };
         
         return observer;
     }



}
