package com.ywp.nice.http.retrofitRx;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface SubscriberListener<T> {

    void onNext(T result);

    void onError(Throwable e);

}
