package com.vincent.drawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Vincent on 2017/10/11.
 */

/**
 * 绘制图形
 */
public class DrawWatch extends View{

    private Paint mPaintCircle;//绘制圆形

    private Paint mPaintScale;//绘制刻度

    private Paint mPaintHour;//绘制时针

    private Paint mPaintMinute;//绘制分针

    private float mWidth;

    private float mHeight;

    public DrawWatch(Context context) {
        this(context,null);
    }

    public DrawWatch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawWatch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mWidth = wm.getDefaultDisplay().getWidth();

        mHeight = wm.getDefaultDisplay().getHeight();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化画笔
        mPaintCircle = new Paint();
        mPaintScale = new Paint();
        mPaintHour = new Paint();
        mPaintMinute = new Paint();


        mPaintCircle.setStyle(Paint.Style.STROKE);//设置空心

        mPaintCircle.setAntiAlias(true);//设置画笔的锯齿效果

        mPaintCircle.setStrokeWidth(5);

        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2,mPaintCircle);


        //画刻度

        mPaintCircle.setStrokeWidth(3);

        for (int i=1;i<=12;i++){

            canvas.rotate(30,mWidth/2,mHeight/2);
            //区分主要的四个点与别的点

            if(i==12||i==3||i==6||i==9){

                mPaintScale.setStrokeWidth(5);
                mPaintScale.setTextSize(90);
                mPaintScale.setColor(Color.BLACK);
                canvas.drawLine(mWidth/2,mHeight/2-mWidth/2,mWidth/2,mHeight/2-mWidth/2+60,mPaintScale);

                String degree = String.valueOf(i);

                canvas.drawText(degree,mWidth/2-mPaintScale.measureText(degree)/2,mHeight/2-mWidth/2+140,mPaintScale);
            }else {
                mPaintScale.setStrokeWidth(3);
                mPaintScale.setTextSize(45);
                mPaintScale.setColor(Color.BLACK);
                canvas.drawLine(mWidth/2,mHeight/2-mWidth/2,mWidth/2,mHeight/2-mWidth/2+30,mPaintScale);

                String degree = String.valueOf(i);

                canvas.drawText(degree,mWidth/2-mPaintScale.measureText(degree)/2,mHeight/2-mWidth/2+90,mPaintScale);
            }


        }

        //绘制两条线

        mPaintHour.setStrokeWidth(20);
        mPaintMinute.setStrokeWidth(10);
        canvas.save();

        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawLine(0,0,100,100,mPaintHour);
        canvas.drawLine(0,0,100,200,mPaintMinute);

    }
}
