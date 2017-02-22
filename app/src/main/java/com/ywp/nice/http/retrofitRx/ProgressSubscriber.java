package com.ywp.nice.http.retrofitRx;

import android.content.Context;
import android.widget.Toast;

import com.ywp.nice.view.WaitDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/20.
 */

public class ProgressSubscriber<T> extends Subscriber {

    private SubscriberListener<T> subscriberListener;
    private Context context;
    //使用自定义的进度条对话框
    private WaitDialog mWaitDialog;

    public ProgressSubscriber(Context context,SubscriberListener<T> subscriberListener) {
        this.subscriberListener = subscriberListener;
        mWaitDialog = new WaitDialog(context);
    }

    public ProgressSubscriber(Context context,SubscriberListener<T> subscriberListener,String message) {
        this.subscriberListener = subscriberListener;
        this.context = context;
        mWaitDialog = new WaitDialog(context);
        mWaitDialog.setMessage(message);
    }

    @Override
    public void onStart() {
        mWaitDialog.show();
    }

    @Override
    public void onCompleted() {
        if (mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (mWaitDialog.isShowing())
            mWaitDialog.dismiss();
        if (subscriberListener != null) {
            subscriberListener.onError(e);
        }
    }

    @Override
    public void onNext(Object o) {
        if (subscriberListener != null) {
            subscriberListener.onNext((T) o);
        }
    }
}
