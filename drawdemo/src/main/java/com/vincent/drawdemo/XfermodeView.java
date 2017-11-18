package com.vincent.drawdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Vincent on 2017/10/13.
 */

public class XfermodeView extends View {

    private Bitmap mBgBitmap, mFgBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private Path mPath;//用于保存手滑动的路径

    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(
                new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//使用DSI_IN模式将路径绘制到灰色的图层上
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);//使笔触和连接处能更加圆滑
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//使笔触和连接处能更加圆滑
        mPath = new Path();
        mBgBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test);
        mFgBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(),
                mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mFgBitmap);

        mCanvas.drawColor(Color.GRAY);//绘制刮刮乐上面灰色的图层
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                break;
        }
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        canvas.drawBitmap(mFgBitmap, 0, 0, null);//绘制灰色的图层
    }

}
