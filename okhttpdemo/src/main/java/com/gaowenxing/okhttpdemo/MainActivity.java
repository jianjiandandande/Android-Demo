package com.gaowenxing.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getRequest(){

        OkHttpClient client = new OkHttpClient();

        String uri = "";

        Request request = new Request.Builder()
                .url(uri)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                //成功

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //失败

            }
        });

    }
}
