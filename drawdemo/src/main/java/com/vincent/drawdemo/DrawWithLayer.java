package com.vincent.drawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.Scroller;

/**
 * Created by Vincent on 2017/10/12.
 */

public class DrawWithLayer extends View{

    private Paint mPaint;
    public DrawWithLayer(Context context) {
        this(context,null);
    }

    public DrawWithLayer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawWithLayer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mPaint = new Paint();
        mPaint.setTextSize(40);
        mPaint.setColor(Color.BLACK);
        canvas.drawColor(Color.WHITE);
        canvas.drawText("图层为半透明状",230,100,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150,150,100,mPaint);

        canvas.saveLayerAlpha(0,0,500,500,127,Canvas.CLIP_TO_LAYER_SAVE_FLAG);//创建图层，并将图层入栈
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200,200,100,mPaint);//绘制到了新创建的图层上
        canvas.restore();//出栈，并将图像绘制到上一个图层中，没有（图层）的话就绘制到屏幕上

        mPaint.setColor(Color.BLACK);
        canvas.drawText("图层为全透明状",230,500,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150,550,100,mPaint);

        canvas.saveLayerAlpha(0,400,500,900,0,Canvas.CLIP_TO_LAYER_SAVE_FLAG);//创建图层，并将图层入栈
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200,600,100,mPaint);//绘制到了新创建的图层上
        canvas.restore();//出栈，并将图像绘制到上一个图层中，没有（图层）的话就绘制到屏幕上
        mPaint.setColor(Color.BLACK);
        canvas.drawText("图层为不透明状",230,900,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150,950,100,mPaint);

        canvas.saveLayerAlpha(0,800,500,1300,255,Canvas.CLIP_TO_LAYER_SAVE_FLAG);//创建图层，并将图层入栈
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200,1000,100,mPaint);//绘制到了新创建的图层上
        canvas.restore();//出栈，并将图像绘制到上一个图层中，没有（图层）的话就绘制到屏幕上
    }
}
