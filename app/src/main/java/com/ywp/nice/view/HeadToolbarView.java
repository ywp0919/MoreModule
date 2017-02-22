package com.ywp.nice.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ywp.nice.R;


/**
 * Created by Administrator on 2016/12/28.
 */

public class HeadToolbarView extends Toolbar {
    public HeadToolbarView(Context context) {
        this(context,null,0);
    }

    public HeadToolbarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadToolbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.toolbar, null);
        addView(rootView);
    }
}
