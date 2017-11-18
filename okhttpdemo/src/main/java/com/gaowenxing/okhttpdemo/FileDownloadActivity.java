package com.gaowenxing.okhttpdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gaowenxing.okhttpdemo.okhttp.ProgressListener;
import com.gaowenxing.okhttpdemo.okhttp.ProgressResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp实现简单的文件下载方式
 */

public class FileDownloadActivity extends AppCompatActivity {

    private Button btn_download;
    private ProgressBar mProgressBar;
    private OkHttpClient mClient;

    private static final String TAG = "FileDownloadActivity";

    private String uri = "http://gdown.baidu.com/data/wisegame/ae0ce73f4b486857/miguyinle_158.apk";
    private String fileName = "net_music.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);

        btn_download = (Button) this.findViewById(R.id.btn_download_simple);
        mProgressBar = (ProgressBar) this.findViewById(R.id.processBar);

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAPK();
            }


        });
        requestPermissions();
//        initOkhttpWithSimple();//普通的下载方式
        initOkhttpWithInterceptor();//使用拦截器方式下载

    }

    private void initOkhttpWithInterceptor() {
        mClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Response response = chain.proceed(chain.request());

                return response.newBuilder().body(new ProgressResponseBody(response.body(), new Prg())).build();
            }
        }).build();

    }

    class Prg implements ProgressListener {

        @Override
        public void onProgress(final int progress) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                }
            });

        }

        @Override
        public void onDone(long totalSize) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FileDownloadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initOkhttpWithSimple() {
        mClient = new OkHttpClient();
    }

    private void downloadAPK() {

        Request request = new Request.Builder()
                .get()
                .url(uri)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求失败" + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    writeFile(response);//普通下载方式

                    writeFileWithInterceptor(response);

                }

            }
        });

    }

    /**
     * 使用拦截器的方式进行下载
     */
    private void writeFileWithInterceptor(Response response) {

        InputStream is = null;
        FileOutputStream os = null;
        is = response.body().byteStream();

        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path, fileName);

        try {
            os = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (is != null) {

                    is.close();
                }
                if (os != null) {

                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                mProgressBar.setProgress(msg.arg1);

                if (msg.arg1 == 100) {

                    Toast.makeText(FileDownloadActivity.this, "下载完成!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void writeFile(Response response) {


        InputStream is = null;
        FileOutputStream os = null;
        is = response.body().byteStream();

        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path, fileName);

        try {
            os = new FileOutputStream(file);
            byte[] bytes = new byte[1024];

            long totalSize = response.body().contentLength();

            long sum = 0;

            int len = 0;
            while ((len = is.read(bytes)) != -1) {

                os.write(bytes);

                sum += len;

                int progress = (int) ((sum * 1.0f / totalSize) * 100);


                Message msg = mHandle.obtainMessage(1);

                msg.arg1 = progress;

                mHandle.sendMessage(msg);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
