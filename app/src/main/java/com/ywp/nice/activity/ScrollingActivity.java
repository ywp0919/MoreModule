package com.ywp.nice.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.ywp.nice.R;
import com.ywp.nice.listener.AppBarStateChangeListener;
import com.ywp.nice.utils.SnackbarUtil;
import com.ywp.nice.utils.Utils;
import com.ywp.nice.view.MyToast;
import com.ywp.nice.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.sc_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            Utils.setToolbarPadding(ScrollingActivity.this, mToolbar);
        }
        AppBarLayout app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    Log.i("onStateChanged", "EXPANDED");
                } else if (state == State.COLLAPSED) {
                    Log.i("onStateChanged", "COLLAPSED");
                } else {
                    Log.i("onStateChanged", "IDLE");
                }
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        NoScrollListView lv_Scrolling = (NoScrollListView) findViewById(R.id.lv_Scrolling);

        List mDatas = new ArrayList();
        for (int i = 0; i < 40; i++) {
            mDatas.add("Item  " + i);
        }
        lv_Scrolling.setAdapter(new ArrayAdapter(ScrollingActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, mDatas));

        final FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarUtil.ShortSnackbar(fab_add, "要添加条目吗？", Snackbar.LENGTH_LONG).setActionTextColor(Color.GREEN).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyToast.makeText(getApplicationContext(),"添加条目",0).show();
                    }
                }).show();
            }
        });

    }

    /**
     * 设置透明状态栏
     * 对4.4及以上版本有效
     *
     * @param on
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
