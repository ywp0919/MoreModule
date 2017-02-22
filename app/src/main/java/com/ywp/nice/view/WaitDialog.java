package com.ywp.nice.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by ywp on 2017/2/9.
 */
public class WaitDialog extends ProgressDialog {
    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置当前的Activity无Title,并且全屏(点用这个方法必须在setContentView之前)
        //设置对话框的外面点击,是否让对话框消失,false是可以取消
        setCanceledOnTouchOutside(false);
        //设置对话框的样式
        setProgressStyle(STYLE_SPINNER);
        //设置进度条显示的内容
        setMessage("正在请求,请稍候...");
        setTitle("网络请求");
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }

}