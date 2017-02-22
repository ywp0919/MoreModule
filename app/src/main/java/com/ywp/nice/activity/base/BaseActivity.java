package com.ywp.nice.activity.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.ywp.nice.http.nohttp.CallServer;
import com.ywp.nice.utils.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/12/21.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;
    private SweetAlertDialog pDialog;

    public abstract Toolbar getToolbar();

    public abstract int getLayout();

    public abstract void init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(getLayout());
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        bind = ButterKnife.bind(this);
        init();
    }


    /**
     * 设置透明状态栏
     * 对4.4及以上版本有效
     *
     * @param on
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setTranslucentStatus(boolean on) {
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

    protected void goActivity(Class clz) {
        startActivity(new Intent(this, clz));
    }

    public <T> void addRequest(int what, Request<T> request, OnResponseListener<T> listener) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(this);
        CallServer.getInstance(2).add(what, request, listener);
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        // 在组件销毁的时候调用队列的按照sign取消的方法即可取消。
        CallServer.getInstance().cancelBySign(this);
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }


    public void showProgressDialog(String title) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在请求...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void dismiss() {
        if (pDialog != null)
            pDialog.dismiss();
    }

    public boolean isShowing() {
        return pDialog.isShowing();
    }

}
