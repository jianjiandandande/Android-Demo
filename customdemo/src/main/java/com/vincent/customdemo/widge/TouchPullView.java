package com.vincent.customdemo.widge;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import com.vincent.customdemo.R;

/**
 * Created by Vincent on 2017/10/14.
 */

public class TouchPullView extends View {

    private Paint mCirclePaint;

    private int mCircleRadius = 80;//半径

    private float mCirclePointX,mCirclePointY;

    private float mProgress;

    private int mDragHeight = 400;//可拖动的最大高度

    private int mTargetWidth = 400 ;//目标宽度
    //贝赛尔曲线的路径和画笔
    private Path mPath = new Path();
    private Paint mPathPaint;

    //重心点的最终宽度，决定控制点的Y坐标
    private int mTargetGravityHeight = 10;
    //角度变换 0~135(切线角度)
    private int mTangentAngle=105;

    private ValueAnimator mValueAnimator;

    private Interpolator mProgressInterpolator = new DecelerateInterpolator();//由快到慢的插值器

    private Interpolator mTangentAngleInterpolator;

    private Drawable mContent = null;

    private int mContentMargin;

    public TouchPullView(Context context) {
        this(context,null);
    }



    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr, defStyleRes);
    }


    /**
     * 初始化
     */
    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        //得到用户设置的参数
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TouchPullView,0,0);

        int color = array.getColor(R.styleable.TouchPullView_pColor,0x20000000);
        mCircleRadius = (int) array.getDimension(R.styleable.TouchPullView_pRadius,mCircleRadius);
        mDragHeight = array.getDimensionPixelOffset(R.styleable.TouchPullView_pDragHeight,mDragHeight);
        mTangentAngle = array.getInteger(R.styleable.TouchPullView_pTangentAngle,100);
        mTargetWidth = array.getDimensionPixelOffset(R.styleable.TouchPullView_pTargetWidth,mTargetWidth);
        mTargetGravityHeight = array.getDimensionPixelOffset(R.styleable.TouchPullView_mTargetGravityHeight,mTargetGravityHeight);

        mContent = array.getDrawable(R.styleable.TouchPullView_pContentDrawable);

        mContentMargin = array.getDimensionPixelOffset(R.styleable.TouchPullView_pContentDrawableMargin,0);
        //销毁
        array.recycle();

        //圆的画笔
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setAntiAlias(true);//设置抗锯齿

        p.setDither(true);//设置防抖动

        p.setStyle(Paint.Style.FILL);//填充方式

        p.setColor(color);

        mCirclePaint = p;


        //初始化路径相关的画笔
        p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setAntiAlias(true);//设置抗锯齿

        p.setDither(true);//设置防抖动

        p.setStyle(Paint.Style.FILL);//填充方式

        p.setColor(color);

        mPathPaint = p;

        //切角路径插值器
        mTangentAngleInterpolator = PathInterpolatorCompat.create(
                (mCircleRadius*2)/mDragHeight,90.0f/mTangentAngle
        );


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //进行基础参数系坐标改变
        int count = canvas.save();//保存画布

        float tranX = (getWidth()-getValueByLine(getWidth(),mTargetWidth,mProgress))/2;

        canvas.translate(tranX,0);

        //画Bezier曲线
        canvas.drawPath(mPath,mPathPaint);

        //画圆
        canvas.drawCircle(mCirclePointX,mCirclePointY,mCircleRadius,mCirclePaint);

        Drawable drawable = mContent;

        if (drawable!=null){
            canvas.save();
            //剪切矩形区域
            canvas.clipRect(drawable.getBounds());
            //绘制Drawable
            drawable.draw(canvas);
            canvas.restore();

        }

        canvas.restoreToCount(count);//画布的复位
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //宽度、高度的大小和意图，父布局给分配的
        int widthMode= MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode= MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //我们需要的宽度和高度的计算
        int iWidth = mCircleRadius*2+getPaddingLeft()+getPaddingRight();

        int iHeight = (int) ((mDragHeight*mProgress+0.5f)+mCircleRadius*2+getPaddingTop()+getPaddingBottom());

        int measureWidth ;
        int measureHeight ;
        if (widthMode==MeasureSpec.EXACTLY){
            measureWidth =width;
        }else if(widthMode==MeasureSpec.AT_MOST){
            measureWidth = Math.min(width,iWidth);
        }else{
            measureWidth = width;
        }

        if (heightMode==MeasureSpec.EXACTLY){
            measureHeight =height;
        }else if(heightMode==MeasureSpec.AT_MOST){
            measureHeight = Math.min(height,iHeight);
        }else{
            measureHeight = height;
        }

        //设置测量的高度和宽度
        setMeasuredDimension(measureWidth,measureHeight);
    }

    /**
     * 当大小改变时触发
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //当高度改变时更新路径
        updatePathLayout();
    }

    /**
     * 设置进度
     * @param progress 进度
     */
    public void setProgress(float progress){
        Log.d("TAG", "setProgress: "+progress);

        mProgress = progress;

        //请求重新进行测量
        requestLayout();
    }

    /**
     * 更新路径
     */
    private void updatePathLayout(){
        //获取进度
        final float progress = mProgress;


        //获取可绘制区域的高度和宽度
        final float w = getValueByLine(getWidth(),mTargetWidth,progress);
        final float h = getValueByLine(0,mDragHeight,progress);

        //圆心的X
        final float cPointX = w/2;
        //圆的半径
        final float cRadius = mCircleRadius;
        //圆心的Y坐标
        final float cPointY = h-cRadius;
        //控制点结束Y的值
        final float endControlY = mTargetGravityHeight;

        //更新圆的坐标
        mCirclePointX = cPointX;
        mCirclePointY = cPointY;
        //路径复位
        final Path path = mPath;
        path.reset();
        path.moveTo(0,0);
        //左边部分的结束点和控制点
        float lEndPointX,lEndPointY;
        float lControlPointX,lControlPointY;

        //获取当前切线弧度
        float angle = mTangentAngle* mTangentAngleInterpolator.getInterpolation(progress);
        double radian = Math.toRadians(angle);
        float x = (float) (Math.sin(radian)*cRadius);
        float y = (float) (Math.cos(radian)*cRadius);

        lEndPointX = cPointX-x;
        lEndPointY = cPointY+y;

        //控制点的Y坐标变化
        lControlPointY = getValueByLine(0,endControlY,progress);

        //控制点与结束点之间Y差值的高度
        float tHeight = lEndPointY - lControlPointY;
        //控制点与结束点之间X差值
        float tWidth = (float) (tHeight/Math.tan(radian));
        lControlPointX = lEndPointX - tWidth;
        //贝塞尔曲线
        path.quadTo(lControlPointX,lControlPointY,lEndPointX,lEndPointY);
        //连接到右边
        path.lineTo(cPointX+(cPointX-lEndPointX),lEndPointY);
        //画右边的贝塞尔曲线
        path.quadTo(cPointX+(cPointX-lControlPointX),lControlPointY,w,0);
        //更新内容部分
        updateContentLayout(cPointX,cPointY,cRadius);
    }

    /**
     * 对内容部分进行测量
     * @param cx 圆心X
     * @param cy 圆心Y
     * @param radius 半径
     */
    private void updateContentLayout(float cx,float cy, float radius){

        Drawable drawable = mContent;
        if (drawable!=null){
            int margin = mContentMargin;
            int l = (int) (cx-radius+margin);
            int r = (int) (cx+radius-margin);
            int t = (int) (cy-radius+margin);
            int b = (int) (cy+radius-margin);
            drawable.setBounds(l,t,r,b);
        }

    }

    /**
     * 获取当前进度的值
     * @param start 起始值
     * @param end 结束值
     * @param progress 进度
     * @return 当前的进度值
     */
    private float getValueByLine(float start,float end,float progress){
        return start+(end - start)*progress;
    }

    /**
     * 添加释放操作(通过属性动画实现)
     */
    public void release(){

        if (mValueAnimator==null){
            ValueAnimator animator = ValueAnimator.ofFloat(mProgress,0f);
            animator.setInterpolator(new DecelerateInterpolator());//由快到慢的插值器
            animator.setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Object val = valueAnimator.getAnimatedValue();

                    if (val instanceof Float){
                        setProgress((Float) val);
                    }
                }
            });
            mValueAnimator =animator;
        }else {
            mValueAnimator.cancel();//有的话先将它取消
            mValueAnimator.setFloatValues(mProgress,0f);//重新设定值
        }
        mValueAnimator.start();//重新启动
    }
}
