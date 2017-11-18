package com.gaowenxing.rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends AppCompatActivity {

    private Api mApi;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApi = retrofit.create(Api.class);

        mTextView = (TextView) this.findViewById(R.id.map_text);

    }

    public void btn_map(View view) {

        /**
         * map操作符简单介绍
         */
//        Observable<Integer> observable = Observable.just(1);
//
//        Observable<String> observableMap = observable.map(new Function<Integer,String>() {
//
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return integer+"";
//            }
//        });//通过map操作符将integer类型转化为String类型
//
//        observableMap.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//
//                //相应的操作
//            }
//        });

        /**
         * flatMap操作符模拟用户登录并获取用户信息
         */
        Observable.just(getUserParam())
                .flatMap(new Function<UserParam, ObservableSource<BaseResult>>() {
                    @Override
                    public ObservableSource<BaseResult> apply(UserParam userParam) throws Exception {

                        BaseResult baseResult = mApi.login(userParam).execute().body();

                        return Observable.just(baseResult);
                    }
                }).flatMap(new Function<BaseResult, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(BaseResult baseResult) throws Exception {

                User user = mApi.getUserInfoWithPath(baseResult.getUser_id()).execute().body();

                return Observable.just(user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        //更新UI

                        mTextView.setText(user.getUsername());
                    }
                });


    }

    private UserParam getUserParam() {

        return new UserParam("vincent", "123456");
    }
}
