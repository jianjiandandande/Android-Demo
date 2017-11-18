package com.gaowenxing.retrofitdemo;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by lx on 2017/8/14.
 */


/**
 * 使用annotation的方式，将接口信息转换为java interface
 */
public interface Api {

    /*
     *  GET请求
     */

    //获取数据，无法传参
    @GET("user/info")
    Call<User> getUserInfo();

    //获取数据，使用Query的方式进行传递具体的参数
    @GET("user/info")
    Call<User> getUserInfoWithQuery(@Query("id") int user_id);

    //获取数据，使用Path的方式进行传递具体的参数
    @GET("user/{id}")
    Call<User> getUserInfoWithPath(@Path("id") int user_id);

    //获取数据，使用QueryMap的方式进行传递具体的参数
    @GET("user/info")
    Call<User> getUserInfoWithMap(@QueryMap Map<String,String> params);


    /*
     *  POST请求
     */

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @POST("user/new")
    Call<BaseResult> saveUser(@Body User user);//默认将User类型转换为json数据


    /**
     * 更新用户信息
     * from表单的形式
     */
    @FormUrlEncoded
    @POST("user/edit")
    Call<BaseResult> editUser(@Field("id") int user_id,@Field("username") String user_name);//默认将User类型转换为json数据


    /**
     * 修改headers信息
     */
    @FormUrlEncoded
    @Headers({"User-Agent: vincent","www.baidu.com"})
    @POST("user/edit")
    Call<BaseResult> headersUser(@Field("id") int user_id,@Field("username") String user_name);//默认将User类型转换为json数据

    /**
     * 用户登录
     */
    @POST("user/login")
    Call<BaseResult> login(@Body UserParam param);

}
