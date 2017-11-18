package com.vincent.customdemo.lockPatter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.vincent.customdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2017/10/23.
 */

public class LockPatternView extends View{

    private static final int POINT_SIZE = 4;//选中点的数量

    private Matrix matrix = new Matrix();//控制线段长短的矩阵

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap pointBitmapNormal,pointBitmapPressed,pointBitmapError,lineBitmapNormal,lineBitmapError;

    private Point[][] mPoints= new Point[3][3];

    private boolean isInit,isSelect,isFinish,movingNoPoint;

    private float mWidth,mHeight,offsetsX,offsetsY,bitmapR,movingX,movingY;

    private List<Point> pointList = new ArrayList<>();//选中的点的集合

    private OnPatterChangeListener mListener;

    private static final String TAG = "LockPatternView";

    public LockPatternView(Context context) {
        super(context);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 初始化点
     */
    private void initPoint() {
        //获取屏幕的宽高
        mWidth = getWidth();
        mHeight = getHeight();

        //横竖屏幕的判断
        //横屏
        if (mWidth>mHeight){

            offsetsX = (mWidth - mHeight)/2;
            mWidth = mHeight;

        }else {
            //竖屏
            offsetsY = (mHeight - mWidth)/2;
            mHeight = mWidth;
        }

        pointBitmapNormal = BitmapFactory.decodeResource(getResources(), R.drawable.point_normal);
        pointBitmapPressed = BitmapFactory.decodeResource(getResources(), R.drawable.point_pressed);
        pointBitmapError = BitmapFactory.decodeResource(getResources(), R.drawable.point_error);
        lineBitmapNormal = BitmapFactory.decodeResource(getResources(),R.drawable.line_normal);
        lineBitmapError = BitmapFactory.decodeResource(getResources(),R.drawable.line_error);

        if(pointBitmapNormal==null){
            Log.d(TAG, "initPoint: `"+"null");
        }

        //第一行
        mPoints[0][0] = new Point(offsetsX+mWidth/4,offsetsY+mWidth/4);
        mPoints[0][1] = new Point(offsetsX+mWidth/2,offsetsY+mWidth/4);
        mPoints[0][2] = new Point(offsetsX+mWidth-mWidth/4,offsetsY+mWidth/4);
        //第二行
        mPoints[1][0] = new Point(offsetsX+mWidth/4,offsetsY+mWidth/2);
        mPoints[1][1] = new Point(offsetsX+mWidth/2,offsetsY+mWidth/2);
        mPoints[1][2] = new Point(offsetsX+mWidth-mWidth/4,offsetsY+mWidth/2);
        //第三行
        mPoints[2][0] = new Point(offsetsX+mWidth/4,offsetsY+mWidth-mWidth/4);
        mPoints[2][1] = new Point(offsetsX+mWidth/2,offsetsY+mWidth-mWidth/4);
        mPoints[2][2] = new Point(offsetsX+mWidth-mWidth/4,offsetsY+mWidth-mWidth/4);

        bitmapR = pointBitmapNormal.getHeight()/2;

        //给每个点设置相应的数字，以便于保存密码

        int index = 1;
        for (Point[] point : mPoints) {
            for (Point point1 : point) {
                point1.index = index;
                index++;
            }
        }

        isInit = true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        movingNoPoint = false;
        isFinish = false;
        movingX = event.getX();
        movingY = event.getY();

        Point point = null;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mListener!=null){
                    mListener.onPatterStart(true);
                }
                resetPoint();
                point = checkSelectPoint();
                if (point!=null){
                    isSelect = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSelect){
                    point = checkSelectPoint();
                    if (point == null){
                        movingNoPoint = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isFinish = true;
                isSelect = false;
                break;
        }
        //选中重复检查
        if (!isFinish && isSelect && point != null){
            if (crossPoint(point)){
                movingNoPoint = true;

            }else {
                point.state = Point.STATE_PRESSED;
                pointList.add(point);
            }
        }

        //绘制结束
        if (isFinish){
            //绘制不成立
            if (pointList.size()==1){

                resetPoint();
                if (mListener != null) {
                    mListener.onPatterChange(null);
                }
            //绘制错误
            }else if(pointList.size()<POINT_SIZE&&pointList.size()>=2){

                errorPoint();
                if (mListener != null) {
                    mListener.onPatterChange(null);
                }
            //绘制成功
            }else {
                if (mListener != null) {
                    String password = "";
                    for (Point point1 : pointList) {
                        password = password+point1.index;
                    }
                    if (!TextUtils.isEmpty(password)) {
                        mListener.onPatterChange(password);
                    }

                }
            }

        }
        postInvalidate();//刷新View
        return true;
    }

    /**
     * 交叉点
     * @param point 点
     * @return 交叉结果
     */
    private boolean crossPoint(Point point){

        if (pointList.contains(point)){
            return true;
        }else {
            return false;
        }

    }
    private Point checkSelectPoint(){

        for (int i = 0; i < mPoints.length; i++) {

            for (int j = 0; j < mPoints[i].length; j++) {

                Point point = mPoints[i][j];

                if (Point.with(point.x,point.y,bitmapR,movingX,movingY)){
                    return point;
                }

            }
        }
        return null;
    }

    //设置绘制不成立
    public void resetPoint(){
        for (Point point : pointList) {
            point.state = Point.STATE_NORMAL;
        }
        pointList.clear();
    }

    //设置绘制不成立
    public void errorPoint(){

        for (Point point : pointList) {
            point.state = Point.STATE_ERROR;
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {

        if (!isInit) {
           initPoint();
        }
        //画点
        points2Canvas(canvas);
        //画线
        if (pointList.size()>0){

            Point start = pointList.get(0);
            for (int i = 0; i < pointList.size(); i++) {
                Point end = pointList.get(i);
                line2Canvas(canvas,start,end);
                start = end;
            }

            //绘制鼠标点
            if (movingNoPoint){
                line2Canvas(canvas,start,new Point(movingX,movingY));
            }

        }
    }


    /**
     * 将点绘制到画布上
     * @param canvas 画布
     */
    private void points2Canvas(Canvas canvas) {

        for (int i = 0; i<mPoints.length;i++){

            for (int j = 0; j<mPoints[i].length;j++){
                Point point = mPoints[i][j];

                if (point.state==Point.STATE_NORMAL){
                    canvas.drawBitmap(pointBitmapNormal,point.x - bitmapR,point.y - bitmapR,mPaint);
                }else if (point.state==Point.STATE_PRESSED){
                    canvas.drawBitmap(pointBitmapPressed,point.x - bitmapR,point.y - bitmapR,mPaint);
                }else {
                    canvas.drawBitmap(pointBitmapError,point.x - bitmapR,point.y - bitmapR,mPaint);
                }
            }

        }
    }

    /**
     * 绘制线段
     * @param canvas
     * @param start
     * @param end
     */
    private void line2Canvas(Canvas canvas,Point start,Point end){

        double lineLength = Point.distance(start,end);
        float degrees = getDegrees(start,end);
        canvas.rotate(degrees,start.x,start.y);
        if(start.state == Point.STATE_PRESSED){
            matrix.setScale((float) (lineLength/lineBitmapNormal.getWidth()),1);//设置x方向的缩放比例
            matrix.postTranslate(start.x - lineBitmapNormal.getWidth()/2,start.y- lineBitmapNormal.getWidth()/2);
            canvas.drawBitmap(lineBitmapNormal,matrix,mPaint);
        }else {
            matrix.setScale((float) (lineLength/lineBitmapError.getWidth()),1);//设置x方向的缩放比例
            matrix.postTranslate(start.x - lineBitmapError.getWidth()/2,start.y- lineBitmapError.getWidth()/2);
            canvas.drawBitmap(lineBitmapError,matrix,mPaint);
        }
        canvas.rotate(-degrees,start.x,start.y);
    }

    /**
     * 计算角度
     * @param start
     * @param end
     * @return
     */
    public static float getDegrees (Point start,Point end) {
        float degrees = 0;
        float startX = start.x;
        float startY = start.y;
        float endX = end.x;
        float endY = end.y;
        if ( startX == endX ) {
            if ( endY > startY ) {
                degrees = 90;
            }
            else {
                degrees = 270;
            }
        }
        else if ( endY == startY ) {
            if ( startX > endX ) {
                degrees = 180;
            }
            else {
                degrees = 0;
            }
        }
        else {
            if ( startX > endX ) {
                if ( startY > endY ) {
                    degrees = 180 + ( float ) ( Math.atan2( startY - endY , startX - endX ) * 180 / Math.PI );
                }
                else {
                    degrees = 180 - ( float ) ( Math.atan2( endY - startY , startX - endX ) * 180 / Math.PI );
                }
            }
            else {
                if ( startY > endY ) {
                    degrees = 360 - ( float ) ( Math.atan2( startY - endY , endX - startX ) * 180 / Math.PI );
                }
                else {
                    degrees = ( float ) ( Math.atan2( endY - startY , endX - startX ) * 180 / Math.PI );
                }
            }
        }
        return degrees;

    }

    /**
     * 自定义点
     */
    public static class Point{

        public static int STATE_NORMAL = 0;
        public static int STATE_PRESSED = 1;
        public static int STATE_ERROR = 2;

        public float x,y;//偏移量

        public int index = 0,state = 0;

        public Point() {
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }


        /**
         * 测量两点之间的距离
         * @param start
         * @param end
         * @return
         */
        public static double distance(Point start,Point end){

            return Math.sqrt(Math.abs(start.x - end.x)*Math.abs(start.x - end.x)+
                    Math.abs(start.y - end.y)*Math.abs(start.y - end.y));

        }

        /**
         * 判断当前鼠标移动的位置是否与某一个点重合
         * @param pointX 点的x坐标
         * @param pointY 点的y坐标
         * @param r 点的半径
         * @param movingX 鼠标的x坐标
         * @param movingY 鼠标的y坐标
         * @return 是否重合
         */
        public static boolean with(float pointX,float pointY,float r,float movingX,float movingY){
            return Math.sqrt((pointX-movingX)*(pointX-movingX)+(pointY-movingY)*(pointY-movingY))<r;
        }
    }

    public static interface OnPatterChangeListener{

        /**
         * 绘制密码
         * @param password
         */
        void onPatterChange(String password);

        void onPatterStart(boolean isStart);
    }

    public void setOnPatterChangeListener(OnPatterChangeListener listener){
        if (listener!=null){
            this.mListener = listener;
        }
    }
}
