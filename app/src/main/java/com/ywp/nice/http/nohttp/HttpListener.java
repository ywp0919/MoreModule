package com.ywp.nice.http.nohttp;

/**
 * Created by Administrator on 2017/2/9.
 */

public interface HttpListener<T> {
    //请求成功时会掉的监听方法
    void onSucceed(int what, T response);

    //请求网络失败回调的监听方法
    void onFailed(int what, T response);
}