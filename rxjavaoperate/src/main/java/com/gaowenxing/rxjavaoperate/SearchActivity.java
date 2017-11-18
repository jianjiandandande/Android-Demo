package com.gaowenxing.rxjavaoperate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * 由于这个Activity中使用到了rxBinding，而当前rxBinding依赖的是rxjava1.x版本，所以在使用时要删除rxjava2的依赖
 */

public class SearchActivity extends AppCompatActivity {

    private EditText mEditText;

    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEditText = (EditText) this.findViewById(R.id.edit_map);

//        getData(mEditText);

        getDataWithRxJava();

    }


    /**
     * 用rxjava的方式实现搜索功能
     */
    private void getDataWithRxJava() {

        //监听Edit/text必须要在UI线程中来做

        Log.d(TAG, "getDataWithRxJava: "+Thread.currentThread().getName());

        RxTextView.textChanges(mEditText)
                .subscribeOn(AndroidSchedulers.mainThread())
                //debounce--延时发送数据
                //filter--过滤数据

                .debounce(200, TimeUnit.MILLISECONDS)
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {

                        //过滤editext中字符为空的情况
                        return charSequence.toString().length()>0;
                    }
                })

                //switchMap的用法与flatMap的用法基本相同，但是switchMap在多次发送数据时，总是以最后一次发送的数据为主，
                // 之前发送的数据 如果在最后一次发送数据时还没有返回结果，那么之前发送的数据会被清空

                .switchMap(new Func1<CharSequence, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(CharSequence charSequence) {
                        //请求网络--search

                        Log.d(TAG, "apply: "+Thread.currentThread().getName());

                        List<String> list = new ArrayList<String>();

                        list.add("abc");
                        list.add("abd");

                        return Observable.just(list);
                    }
                })
//                .flatMap(new Func1<CharSequence, Observable<List<String>>>() {
//                    @Override
//                    public Observable<List<String>> call(CharSequence charSequence) {
//                        //请求网络--search
//
//                        Log.d(TAG, "apply: "+Thread.currentThread().getName());
//
//                        List<String> list = new ArrayList<String>();
//
//                        list.add("abc");
//                        list.add("abd");
//
//                        return Observable.just(list);
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {

                    @Override
                    public void call(List<String> strings) {

                        Log.d(TAG, "call: "+Thread.currentThread().getName());

                        Log.d(TAG, "call: list = "+strings);

                    }
                });
    }


    /**
     * 传统的搜索实现方式
     * @param editText
     */
    private void getData(EditText editText){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                search(s.toString());

            }
        });

    }

    /**
     * 搜索功能
     */
    private void search(String value) {
    }
}
