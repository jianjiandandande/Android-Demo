package com.vincent.customdemo.BezierLearn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Vincent on 2017/10/15.
 */

public class OneToThree extends View {
    public OneToThree(Context context) {
        super(context);
        init();
    }

    public OneToThree(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OneToThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OneToThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Path mPath = new Path();

    private void init(){


        Paint paint = mPaint;

        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//抗抖动
        paint.setStyle(Paint.Style.STROKE);//只绘制边缘线
        paint.setStrokeWidth(20);//5个像素点


        //一阶贝赛尔曲线
        Path path = mPath;
        path.moveTo(100,100);
        path.lineTo(300,300);

        //二阶贝赛尔曲线

        //path.quadTo(500,0,700,300);

        //相对的一种实现,相对于上一次结束时的坐标
        path.rQuadTo(200,-300,400,0);


        path.moveTo(400,800);
        //三阶贝塞尔曲线
        //path.cubicTo(500,600,700,1200,800,800);

        //相对的一种实现
        path.rCubicTo(100,-200,300,400,400,0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);//绘制一节贝塞尔曲线

        canvas.drawPoint(500,0,mPaint);//绘制二阶贝赛尔曲线的控制点

        canvas.drawPoint(500,600,mPaint);//绘制三阶贝赛尔曲线的控制点
        canvas.drawPoint(700,1200,mPaint);//绘制三阶贝赛尔曲线的控制点
    }
}
