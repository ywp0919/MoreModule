package com.ywp.nice.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;


public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private int sinceDirectionChange;

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //1.判断滑动的方向 我们需要垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //2.根据滑动的距离显示和隐藏footer view
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child,
                                  View target, int dx, int dy, int[] consumed) {
        if (dy > 0 && sinceDirectionChange < 0 || dy < 0 && sinceDirectionChange > 0) {
            child.animate().cancel();
            sinceDirectionChange = 0;
        }
        sinceDirectionChange += dy;
        int visibility = child.getVisibility();
        if (sinceDirectionChange > child.getHeight() && visibility == View.VISIBLE) {
            hide(child);
        } else {
            if (sinceDirectionChange < 0 && (visibility == View.GONE || visibility == View
                    .INVISIBLE)) {
                show(child);
            }
        }
    }

    private void hide(final View view) {
//        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight()).
//                setInterpolator(INTERPOLATOR).setDuration(100);
//        animator.setListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1l, 0);
        alphaAnimation.setDuration(1000);
        view.setAnimation(alphaAnimation);
        view.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//                show(view);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        animator.start();
    }

    private void show(final View view) {
//        ViewPropertyAnimator animator = view.animate().translationY(0).
//                setInterpolator(INTERPOLATOR).
//                setDuration(100);
//        animator.setListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1l);
        alphaAnimation.setDuration(1000);
        view.setAnimation(alphaAnimation);
        view.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//                hide(view);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        animator.start();

    }
}
