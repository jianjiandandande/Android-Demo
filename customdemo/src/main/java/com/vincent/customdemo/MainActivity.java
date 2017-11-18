package com.vincent.customdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.vincent.customdemo.widge.TouchPullView;

public class MainActivity extends AppCompatActivity {

    private static final float TOUCH_MOVE_MAX_Y = 600;

    private float mTouchMoveStartY = 0;

    private TouchPullView mTouchPullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTouchPullView = (TouchPullView) this.findViewById(R.id.touchPull);



        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int action = motionEvent.getActionMasked();

                switch(action){
                    case MotionEvent.ACTION_DOWN:
                        mTouchMoveStartY = motionEvent.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:

                        float y = motionEvent.getY();

                        if (y>=mTouchMoveStartY){

                            float moveSize = y - mTouchMoveStartY;

                            float progress = moveSize>TOUCH_MOVE_MAX_Y?
                                    1:moveSize/TOUCH_MOVE_MAX_Y;

                            mTouchPullView.setProgress(progress);
                        }

                        return true;
                    case MotionEvent.ACTION_UP:
                        mTouchPullView.release();
                        Log.d("TAG", "onTouch: "+"up up");
                        return  true;
                }

                return false;
            }
        });
    }
}
