package com.vincent.drawdemo.SurfaceView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Vincent on 2017/10/13.
 *
 * SurfaceView的简单使用
 * 1.实现SurfaceHolder.Callback和Runnable接口
 * 2.创建SurfaceHolder、Canvas、mIsDrawing(boolean)
 * 3.在surfaceCreated中设置mIsDrawing
 * 4.在构造方法中初始化SurfaceHolder
 */

public class SurfaceViewTemplate extends SurfaceView
        implements SurfaceHolder.Callback, Runnable {

    // SurfaceHolder
    private SurfaceHolder mHolder;
    // 用于绘图的Canvas
    private Canvas mCanvas;
    // 子线程标志位
    private boolean mIsDrawing;

    private int x = 0;
    private int y = 0;
    private Path mPath;
    private Paint mPaint;

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //mHolder.setFormat(PixelFormat.OPAQUE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        mPath.moveTo(0,400);
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
            x++;
            y= (int) (100*Math.sin(x*2*Math.PI/100)+400);
            mPath.lineTo(x,y);
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();

            mCanvas.drawColor(Color.WHITE);//SurfaceView的背景
            mCanvas.drawPath(mPath,mPaint);

        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}