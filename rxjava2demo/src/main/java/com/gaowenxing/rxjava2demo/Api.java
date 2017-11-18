package com.gaowenxing.rxjava2demo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
}
