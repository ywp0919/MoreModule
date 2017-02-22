package com.ywp.nice.http.nohttp;

import android.content.Context;
import android.content.DialogInterface;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.ywp.nice.view.WaitDialog;

/**
 * Created by Administrator on 2017/2/9.
 */

public class HttpResponseListener<T> implements OnResponseListener {
    //使用自定义的进度条对话框
    private WaitDialog mWaitDialog;
    //之前定义的监听方法的接口类
    private HttpListener<T> callback;
    //一个标识,看是否加载
    private boolean isLoading;
    //一个NoHttp队列
    private Request<?> mRequest;
    //上下文
    private Context context;
    //一个标识,判断进度条对话框是否可以取消
    private boolean canCancle;

    /**
     * @param callback 自定义的接口对象,就是复写Nohttp成功失败的那个类
     * @param isLoading 判断进度条对话框是否可以取消
     * @param request   Nohttp的队列对象
     * @param context   上下文
     * @param canCancle
     */
    public HttpResponseListener(Context context, Request<?> request, HttpListener<T> callback, boolean isLoading, boolean canCancle) {
        this.callback = callback;
        this.isLoading = isLoading;
        mRequest = request;
        this.context = context;
        this.canCancle = canCancle;
        if (context != null && isLoading) {
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancle);
            //设置监听器
            //当对话框点击外面可以取消,那么就让他取消的逻辑代码
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    HttpResponseListener.this.mRequest.cancel();
                }
            });
        }
    }

    //请求网络开始时回调,进度条对话框的显示
    @Override
    public void onStart(int what) {
        //判断进度条对话框是否处于夹在状态,进度条对话框对象是否存在,进度条对话框是否存在
        if (isLoading && mWaitDialog != null && !mWaitDialog.isShowing()) {
            //显示对话框
            mWaitDialog.show();
        }
    }

    //网络请求成功时回调,代码直接运行在主线程
    @Override
    public void onSucceed(int what, Response response) {
        //使用的是自己定义的接口,先判断接口对象是否为Null,不为Null时执行
        if (callback != null) {
            callback.onSucceed(what, (T) response.get());
        }
    }

    //网络请求失败时回调,代码直接运行在主线程
    @Override
    public void onFailed(int what, Response response) {
        //使用的是自己定义的接口,先判断接口对象是否为Null,为Null时执行
        if (callback != null) {
            callback.onFailed(what, (T) response.get());
        }
    }

    //请求结束时进度条对话框隐藏
    @Override
    public void onFinish(int what) {
        //判断进度条对话框是否处于夹在状态,进度条对话框对象是否存在,进度条对话框是否存在
        if (isLoading && mWaitDialog != null && mWaitDialog.isShowing()) {
            //隐藏对话框
            mWaitDialog.dismiss();
        }
    }
}