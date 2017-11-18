package com.vincent.customdemo.BezierLearn;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Vincent on 2017/10/15.
 */

public class BezierView extends View {
    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private Path mBezier = new Path();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path mSrcBezier = new Path();

    private void init(){
        //初始化画笔参数
        Paint paint = mPaint;

        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//抗抖动
        paint.setStyle(Paint.Style.STROKE);//只绘制边缘线
        paint.setStrokeWidth(10);//5个像素点

        //初始化源贝赛尔曲线
        mSrcBezier.cubicTo(200,700,500,1200,700,200);
        //初始化贝赛尔曲线4阶
        new Thread(new Runnable() {
            @Override
            public void run() {
                initBezier();
            }
        }).start();
    }

    /**
     * 初始化贝赛尔曲线
     */
    private void initBezier(){
        float[] xPoints = new float[]{0,200,500,700};//要变化5，6，7阶贝赛尔曲线，只需要加点就可以实现
        float[] yPoints = new float[]{0,700,1200,200};


        Path path = mBezier;

        int fps = 10000;

        for (int i=0;i<=fps;i++){
            float progress = i/(float)fps;//进度，相当于贝赛尔曲线中的t
            float x = calculateBezier(progress,xPoints);
            float y = calculateBezier(progress,yPoints);
            path.lineTo(x,y);

            postInvalidate();//刷新页面

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 计算某时刻贝赛尔曲线所处的值
     * @param t 时间 (0~1)
     * @param values 贝赛尔点集合(x 或 y)
     * @return t时刻贝赛尔曲线所处的点的坐标
     */
    private float calculateBezier(float t,float... values){
        //采用双重循环
        final  int len = values.length;

        for (int i=len-1;i>0;i--){

            for (int j=0;j<i;j++){
                values[j] = values[j]+(values[j+1]-values[j])*t;
            }

        }

        //运算时结果保存在第一位
        return values[0];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(0x40000000);
        canvas.drawPath(mSrcBezier,mPaint);//源贝赛尔曲线
        mPaint.setColor(Color.RED);
        canvas.drawPath(mBezier,mPaint);//通过自己的算法实现的贝赛尔曲线
    }
}
