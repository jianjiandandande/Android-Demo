package com.vincent.drawdemo.SurfaceView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vincent on 2017/10/13.
 */

public class SurfaceViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SurfaceViewTemplate(this));
    }
}
