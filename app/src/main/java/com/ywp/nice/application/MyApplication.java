package com.ywp.nice.application;

import android.app.Application;

import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.cache.DBCacheStore;
import com.yolanda.nohttp.cookie.DBCookieStore;

/**
 * Created by Administrator on 2016/12/15.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        initNoHttp();

    }

    public static MyApplication getInstance() {
        return instance;
    }

    private void initNoHttp() {

        Logger.setDebug(true);  // 启动nohttp日志管理
        Logger.setTag("NoHttpDebug");

        // 初始化配置NoHttp
        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(15 * 1000)
                .setReadTimeout(15 * 1000)
                .setNetworkExecutor(new OkHttpNetworkExecutor())
                .setCacheStore(new DBCacheStore(this)
                        .setEnable(true))
                .setCookieStore(new DBCookieStore(this)
                        .setEnable(true))
        );
    }
    
    public static Application getAppInstance(){
        return instance;
    }
}
