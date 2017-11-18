package com.gaowenxing.okhttpdemo.LocalServer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.gaowenxing.okhttpdemo.R;


public class LocalServerActivity extends AppCompatActivity {

    private TextView mTextView;

    private ImageView mImageView;

    private OkHttpClient mClient;

    private String mBaseUrl = "http://192.168.30.2:8080/imooc_okhttp/";

    private static final String TAG = "LocalServerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_server);

        mTextView = (TextView) findViewById(R.id.tv_result);
        mImageView = (ImageView) findViewById(R.id.image_result);
        mClient = new OkHttpClient();
        requestPermissions();
    }

    /**
     * get请求
     * @param view
     */
    public void doGet(View view) {

        Request request = new Request.Builder()
                .get()
                .url(mBaseUrl+"login?username=jianjiandandan&password=123")
                .build();

        enqueueRequest(request);

    }

    /**
     * post请求
     * @param view
     */
    public void doPost(View view) {

        FormBody body = new FormBody.Builder()
                .add("username","Android")
                .add("password","12345")
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(mBaseUrl+"login")
                .build();
        enqueueRequest(request);
    }
    /**
     * 使用post发送一个字符串到服务器
     * @param view
     */
    public void doPostString(View view) {

        RequestBody body = RequestBody.create(MediaType.
                parse("text/plain;charset=utf-8"),
                "{username:java,password:12445}");
        Request request = new Request.Builder()
                .post(body)
                .url(mBaseUrl+"doPostString")
                .build();
        enqueueRequest(request);

    }
    /**
     * 使用post发送一个文件到服务器
     * @param view
     */
    public void doPostFile(View view) {

        File file = new File(Environment.getExternalStorageDirectory(),"test.jpg");

        if (!file.exists()){

            Log.d("TAG", "doPostFile: "+file.getAbsolutePath()+" not exist!");
            return;
        }

        RequestBody body = RequestBody.create(MediaType.
                        parse("application/octet-stream"),
                file);
        Request request = new Request.Builder()
                .post(body)
                .url(mBaseUrl+"doPostFile")
                .build();
        enqueueRequest(request);
    }

    public void doPostUpload(View view) {

        File file = new File(Environment.getExternalStorageDirectory(),"download1111.jpg");

        if (!file.exists()){

            Log.d("TAG", "doPostFile: "+file.getAbsolutePath()+" not exist!");
            return;
        }

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username","vincent")
                .addFormDataPart("password","123456")
                .addFormDataPart("mPhoto","vincent.jpg",RequestBody.create(
                        MediaType. parse("application/octet-stream"),
                        file)).build();

        CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
            @Override
            public void countProgress(final long buffWritten, final long contentLength) {
                Log.d(TAG, "countProgress: 上传进度："+(buffWritten/contentLength)*100+"%");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText((buffWritten/contentLength)*100+"%");
                    }
                });

            }
        });

        Request request = new Request.Builder()
                .post(countingRequestBody)
                .url(mBaseUrl+"uploadInfo")
                .build();
        enqueueRequest(request);
    }

    /**
     * 文件的下载
     * @param view
     */
    public void doDownload(View view) {

        Request request = new Request.Builder()
                .get()
                .url(mBaseUrl+"files/vincent.jpg")
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: "+e.getMessage());
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    InputStream is = response.body().byteStream();

                    final long countLength = response.body().contentLength();

                    float sum = 0;

                    int len = 0;
                    byte[] buff = new byte[128];

                    File file = new File(Environment.getExternalStorageDirectory(), "download1111.jpg");

                    FileOutputStream fos = new FileOutputStream(file);

                    while ((len = is.read(buff)) != -1) {
                        fos.write(buff, 0, len);

                        sum +=len;
                        final float finalSum = sum;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText((finalSum/countLength)*100+"%");//计算下载进度并作显示
                            }
                        });

                        Log.d(TAG, "onResponse: 下载进度："+(sum/countLength)*100+"%");

                    }

                    fos.flush();

                    fos.close();

                    is.close();

                    Log.d(TAG, "onResponse: success!");

                }
            }
        });
    }

    /**
     * 加载图片
     * @param view
     */
    public void doDownloadImage(View view) {

        Request request = new Request.Builder()
                .get()
                .url(mBaseUrl+"files/vincent.jpg")
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: "+e.getMessage());
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    InputStream is = response.body().byteStream();

                    final Bitmap bitmap = BitmapFactory.decodeStream(is);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(bitmap);
                        }
                    });

                    Log.d(TAG, "onResponse: success!");

                }
            }
        });
    }


    private void enqueueRequest(Request request) {
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(string);
                    }
                });
            }
        });
    }

    private static final int EXTERNAL_STORAGE_REQ_CODE = 1;

    private void requestPermissions() {
        //判断当前Activity是否已经具有了该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //如果APP的权限被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "读写权限已经被拒绝，你可以在设置中进行开启!", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQ_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE:
                //如果权限的请求被拒绝，grantResults数组的长度为空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //申请成功
                    Toast.makeText(this, "已经获取到权限了", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "获取权限异常！", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


}
