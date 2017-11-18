package com.vincent.animationdemo.ProPertyAnimation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.vincent.animationdemo.R;

/**
 * Created by Vincent on 2017/10/13.
 * ObjectAnimator可以完成一个比较细化的动画效果，它只控制某个需要动画的View的一个属性值(该属性值必须有get、set方法)
 * ObjectAnimator所操作的那个属性值如果没有get、set方法，那么可以通过自定义一个属性类，或者包装类来为该属性添加get、set方法
 */

public class PropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_property);
    }

    public void translate(View view) {

        Button btn = (Button) view;

        ObjectAnimator animator = ObjectAnimator.ofFloat(btn,"translationX",300);

        animator.start();

    }
}
