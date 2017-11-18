package com.gaowenxing.rxjavaoperate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lx on 2017/8/15.
 */

public interface Api {


    /**
     * 获取用户信息
     * @param user_id
     * @return
     */
    @GET("user/{id}")
    Call<User> getUserInfoWithPath(@Path("id") int user_id);


    @POST("login/gson")
    Call<BaseResult> login(@Body UserParam userParam);


    /**
     * 获取网络中的Course,与本地进行合并
     */
    @GET("courses")
    Observable<List<Course>> getCourse();
}
