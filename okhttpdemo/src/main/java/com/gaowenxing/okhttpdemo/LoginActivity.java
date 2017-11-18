package com.gaowenxing.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
* post请求
* */

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btn_login;
    private OkHttpClient mClient;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        btn_login = (Button) this.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                loginWithJson(usernameText,passwordText);
            }
        });

        mClient = new OkHttpClient();
    }

    /**
     * post请求中的json形式
     * @param username
     * @param password
     */
    private void loginWithJson(String username, String password)  {

        String uri = Config.API.BASE_URI+"login/json";

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username",username);
            jsonObj.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String jsonParams = jsonObj.toString();

        Log.i(TAG, "jsonParams = "+jsonParams);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonParams);


        final Request request = new Request.Builder()
                .url(uri)
                .post(body)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求服务器失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    String msg = jsonObj.optString("message");
                    int success = jsonObj.optInt("success");

                    if (success==1){
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * post请求中的from形式
     * @param username
     * @param password
     */
    private void loginWithFrom(String username,String password){

        String uri = Config.API.BASE_URI+"login";

        FormBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();


        final Request request = new Request.Builder()
                .url(uri)
                .post(body)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求服务器失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    String msg = jsonObj.optString("message");
                    int success = jsonObj.optInt("success");

                    if (success==1){
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
