package com.vincent.drawdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by Vincent on 2017/10/12.
 */


/**
 * 通过Layer来实现图层叠加效果
 */
public class LayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawWithLayer(this));
    }
}
