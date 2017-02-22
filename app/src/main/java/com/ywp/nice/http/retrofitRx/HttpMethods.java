package com.ywp.nice.http.retrofitRx;

import com.ywp.nice.http.retrofitRx.api.GankApi;
import com.ywp.nice.http.retrofitRx.api.XyApi;
import com.ywp.nice.model.LoginResultModel;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/20.
 */

public class HttpMethods extends RetrofitUtils {


    private static GankApi gankApi = getRetrofit().create(GankApi.class);

    private static XyApi xyApi = getRetrofit().create(XyApi.class);

    public static XyApi getXyApi() {
        return xyApi;
    }

    public static GankApi getGankApi() {
        return gankApi;
    }

    public static void doLogin(String username, String password, Observer<LoginResultModel> observer) {
        setSubscribe(getXyApi().doLogin(username, password), observer);
    }


    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
