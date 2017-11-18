package com.vincent.animationdemo;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * ValueAnimation是ObjectAnimator的基类，它是属性动画的核心所在
 * ValueAnimation本身不提供任何的动画效果，它更像一个数值发生器，用来产生一定规律的数字
 */

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
    }

    public void tvTimer(final View view) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(5,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((TextView)view).setText("$"+(Integer)valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }
}
