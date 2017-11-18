package com.gaowenxing.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/**
 * get请求
 */

public class UserInfoActivity extends AppCompatActivity {

    private Button btn_get;
    private ImageView mImageView;
    private TextView mTextView;

    private static final String TAG = "UserInfoActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        btn_get = (Button) this.findViewById(R.id.button);
        mImageView = (ImageView) this.findViewById(R.id.imageView);
        mTextView = (TextView) this.findViewById(R.id.text);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
            }

            
        });

    }

    private void getUserInfo() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url("http://192.168.1.189:5000/user/info?id=1")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){

                    String json=response.body().string();//json中包含了id,head,username三个字段

                    showUser(json);
                }

            }
        });
    }

    private void showUser(final String json) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonobj = new JSONObject(json);

                    String id = jsonobj.optString("id");
                    String head_uri = jsonobj.optString("head");
                    String username = jsonobj.optString("username");
                    mTextView.setText(username);

                    Picasso.with(UserInfoActivity.this)
                            .load(head_uri)
                            .resize(200,200)
                            .centerCrop()
                            .into(mImageView);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
