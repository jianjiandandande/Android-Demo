package com.gaowenxing.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private Button btn_request;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_request = (Button) this.findViewById(R.id.btn_request);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.189:5000/")
                .addConverterFactory(GsonConverterFactory.create())//配置gson转换器,以工厂模式实现
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//与rxjava进行交互
                .build();


    }

    private void requestAPI(View view){

        Call<User> call = api.getUserInfo();

        call.enqueue(new Callback<User>() {

            /**
             * 与okhttp3相似，不同的地方是他多了泛型
             *
             * onResponse运行在主线程当中，okhttp中的onResponse方法不在主线程中运行
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Toast.makeText(MainActivity.this, "username = " +response.body().getUsername(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "id = " +response.body().getId(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    /**
     * 用retrofit方式实现简单的用户登录，以及登录之后获取用户信息
     * @param view
     */

    public void login(View view) {


        api.login(new UserParam("vincent","123456")).enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {

                if (response.isSuccessful()){

                    int user_id = response.body().getUser_id();

                    api.getUserInfoWithPath(user_id).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (response.isSuccessful()){

                                //更新ui(记得转换到主线程)


                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {

            }
        });
    }

    public void loginWithRxava(View view) {

        
    }
}
