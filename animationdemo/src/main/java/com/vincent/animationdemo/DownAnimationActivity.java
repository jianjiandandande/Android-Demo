package com.vincent.animationdemo;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DownAnimationActivity extends AppCompatActivity {

    private LinearLayout mHiddenView;

    private float mDensity;

    private int mHiddenViewMeasuredHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_animation);
        mHiddenView = (LinearLayout) findViewById(R.id.hidden_view);
        //获取像素密度
        mDensity = getResources().getDisplayMetrics().density;
        
        //获取布局的高度
        mHiddenViewMeasuredHeight = (int) (mDensity*40+0.5);

    }

    public void llClick(View view) {
        
        if (mHiddenView.getVisibility()==View.GONE){
            animateOpen(mHiddenView);
        }else {
            animateClose(mHiddenView);
        }
    }

    private void animateClose(LinearLayout hiddenView) {
        hiddenView.setVisibility(View.GONE);
        ValueAnimator animator = createDropAnimator(
                hiddenView,
                0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animateOpen(LinearLayout hiddenView) {
        hiddenView.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(
                hiddenView,
                0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    /**
     * 实现下拉逻辑
     * @param view
     * @param start
     * @param end
     * @return
     */
    private ValueAnimator createDropAnimator(
            final View view, int start, int end) {

        final ValueAnimator animator = ValueAnimator.ofInt(start,end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) animator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);

            }
        });
        return animator;
    }
}
