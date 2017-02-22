package com.ywp.nice.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wingsofts.dragphotoview.DragPhotoView;
import com.ywp.nice.R;

public class ShowPhotoActivity extends AppCompatActivity {

    private static final int DEFAULT_DURATION = 300;
    private DragPhotoView mPhoto;

    int mOriginLeft;
    int mOriginTop;
    int mOriginHeight;
    int mOriginWidth;
    int mOriginCenterX;
    int mOriginCenterY;
    private float mTargetHeight;
    private float mTargetWidth;
    private float mScaleX;
    private float mScaleY;
    private float mTranslationX;
    private float mTranslationY;
    private RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mPhoto = (DragPhotoView) findViewById(R.id.pv_photo);
        rootView = (RelativeLayout) findViewById(R.id.activity_show_photo);

        String path = getIntent().getStringExtra("path");
        Glide.with(this).load(path).placeholder(R.drawable.ic_ico).error(R.drawable.ic_ico).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mPhoto.setImageDrawable(resource);
                rootView.setBackgroundColor(Color.BLACK);
                mPhoto.setVisibility(View.VISIBLE);
                // 安卓5.0以上共享元素实现方式
//                ViewCompat.setTransitionName(mPhoto,"img");
                //兼容低版本共享元素实现方式
                mPhoto.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        mPhoto.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        mOriginLeft = getIntent().getIntExtra("left", 0);
                        mOriginTop = getIntent().getIntExtra("top", 0);
                        mOriginHeight = getIntent().getIntExtra("height", 0);
                        mOriginWidth = getIntent().getIntExtra("width", 0);
                        mOriginCenterX = mOriginLeft + mOriginWidth / 2;
                        mOriginCenterY = mOriginTop + mOriginHeight / 2;

                        int[] location = new int[2];


                        mPhoto.getLocationOnScreen(location);

                        mTargetHeight = (float) mPhoto.getHeight();
                        mTargetWidth = (float) mPhoto.getWidth();
                        mScaleX = (float) mOriginWidth / mTargetWidth;
                        mScaleY = (float) mOriginHeight / mTargetHeight;

                        float targetCenterX = location[0] + mTargetWidth / 2;
                        float targetCenterY = location[1] + mTargetHeight / 2;

                        mTranslationX = mOriginCenterX - targetCenterX;
                        mTranslationY = mOriginCenterY - targetCenterY;
                        mPhoto.setTranslationX(mTranslationX);
                        mPhoto.setTranslationY(mTranslationY);

                        mPhoto.setScaleX(mScaleX);
                        mPhoto.setScaleY(mScaleY);


                        mPhoto.setMinScale(mScaleX);
                        runEnterAnimation();
//                performEnterAnimation();

                    }
                });
            }
        });

//        mPhoto.setImageResource(R.drawable.ic_ico);
        mPhoto.setOnExitListener(new DragPhotoView.OnExitListener() {
            @Override
            public void onExit(DragPhotoView dragPhotoView, float v, float v1, float v2, float v3) {
                runExitAnimation();
            }
        });

        mPhoto.setOnTapListener(new DragPhotoView.OnTapListener() {
            @Override
            public void onTap(DragPhotoView dragPhotoView) {
                runExitAnimation();
            }
        });


    }

    private void runEnterAnimation() {
        mPhoto.animate()
                .setDuration(DEFAULT_DURATION)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0)
                .translationY(0)
                .start();
    }

    private void runExitAnimation() {
        rootView.setBackgroundColor(Color.TRANSPARENT);
        mPhoto.animate()
                .setDuration(DEFAULT_DURATION)
                .alpha(0.2f)
                .scaleX(mScaleX)
                .scaleY(mScaleY)
                .translationX(mTranslationX)
                .translationY(mTranslationY)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }).start();
    }

    private Bundle captureValues(@NonNull View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt("left", screenLocation[0]);
        b.putInt("top", screenLocation[1]);
        b.putInt("width", view.getWidth());
        b.putInt("height", view.getHeight());
        return b;
    }


    @Override
    public void onBackPressed() {
        runExitAnimation();
    }
}
