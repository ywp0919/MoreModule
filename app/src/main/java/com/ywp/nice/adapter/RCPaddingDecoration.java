package com.ywp.nice.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/2/8.
 */

public class RCPaddingDecoration extends RecyclerView.ItemDecoration {
    private  Context mContext;
    private Drawable divDrawable;
//    private static final int[] ATTRS = new int[]{
//            android.R.attr.listDivider
//    };


    public RCPaddingDecoration(Context context,int res){
//        final TypedArray a = context.obtainStyledAttributes(ATTRS);
//        divDrawable = a.getDrawable(0);
        mContext = context;
        divDrawable = mContext.getResources().getDrawable(res);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = divDrawable.getIntrinsicHeight();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divDrawable.getIntrinsicHeight();
            divDrawable.setBounds(left, top, right, bottom);
            divDrawable.draw(c);
        }
    }
}
