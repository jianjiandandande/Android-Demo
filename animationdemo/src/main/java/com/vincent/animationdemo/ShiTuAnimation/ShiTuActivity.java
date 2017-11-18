package com.vincent.animationdemo.ShiTuAnimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vincent.animationdemo.R;

public class ShiTuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button alpha;
    private Button rotate;
    private Button translate;
    private Button scale;
    private Button set;

    private Animation alphaAnimation,rotateAnimation,translateAnimation,scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_tu);

        alpha = (Button) this.findViewById(R.id.btn_alpha);
        rotate = (Button) this.findViewById(R.id.btn_rotate);
        translate = (Button) this.findViewById(R.id.btn_translate);
        scale = (Button) this.findViewById(R.id.btn_scale);
        set = (Button) this.findViewById(R.id.btn_set);

        alpha.setOnClickListener(this);
        rotate.setOnClickListener(this);
        translate.setOnClickListener(this);
        scale.setOnClickListener(this);
        set.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_alpha:
                //透明度动画
                alphaAnimation = new AlphaAnimation(0,1);

                alphaAnimation.setDuration(1000);

                alpha.startAnimation(alphaAnimation);
                Toast.makeText(this, alpha.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_rotate:
                rotateAnimation = new RotateAnimation(0,360,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);
                rotateAnimation.setDuration(1000);
                rotate.startAnimation(rotateAnimation);
                Toast.makeText(this, rotate.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_translate:
                translateAnimation = new TranslateAnimation(translate.getLeft(),
                        translate.getTop(),
                        translate.getLeft(),
                        translate.getTop()+300);
                translateAnimation.setDuration(1000);
                translate.startAnimation(translateAnimation);
                Toast.makeText(this, translate.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_scale:
                //缩放动画
                scaleAnimation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);

                scaleAnimation.setDuration(1000);

                scale.startAnimation(scaleAnimation);
                Toast.makeText(this, scale.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_set:
                //缩放动画

                AnimationSet animations = new AnimationSet(true);

                animations.setDuration(1000);

                animations.addAnimation(alphaAnimation);
                animations.addAnimation(rotateAnimation);
                animations.addAnimation(translateAnimation);
                animations.addAnimation(scaleAnimation);

                set.startAnimation(animations);
                Toast.makeText(this, set.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
