package com.vincent.drawdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage1,mImage2,mImage3,mImage4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawWatch(this));


       /* mImage1 = (ImageView) this.findViewById(R.id.image1);
        mImage2 = (ImageView) this.findViewById(R.id.image1);
        mImage3 = (ImageView) this.findViewById(R.id.image1);
        mImage4 = (ImageView) this.findViewById(R.id.image1);

        Drawable bitmap1 = getResources().getDrawable(R.drawable.bitmap_resource,null);//bitmap.xml实现

        mImage1.setImageDrawable(bitmap1);*/

    }
}
