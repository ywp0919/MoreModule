package com.ywp.nice.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.yolanda.nohttp.PosterHandler;
import com.ywp.nice.R;
import com.ywp.nice.activity.LoginActivity;
import com.ywp.nice.view.ColorfulTextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView iv_ico = (ImageView) findViewById(R.id.iv_ico);
        ColorfulTextView ctv_title = (ColorfulTextView) findViewById(R.id.ctv_title);

        AnimationSet set = new AnimationSet(true);

        AlphaAnimation alphaAnimationText = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimationText.setDuration(3000);
        alphaAnimationText.setFillAfter(true);


        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillAfter(true);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 1080,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);
        scaleAnimation.setFillAfter(true);

        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(rotateAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PosterHandler.getInstance().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                        WelcomeActivity.this.finish();
                    }
                },1100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ctv_title.startAnimation(alphaAnimationText);
        iv_ico.startAnimation(set);

    }
}
