package com.ywp.nice.http.retrofitRx.api;

import com.ywp.nice.http.Urls;
import com.ywp.nice.model.LoginResultModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface XyApi {
/*
    @GET GET请求方式
    @POST POST请求方式
    @Query GET请求参数
    @Header用来添加Header请求头
    @FormUrlEncoded post请求头标示
    其他注解请求方式：
    @PUT
    表示这是一个PUT请求
    @DELETE 表示这是一个DELETE请求
    @HEAD
    表示这是一个HEAD请求
    @OPTIONS 表示这是一个OPTION请求
    @PATCH
    表示这是一个PAT请求*/


    @GET(Urls.HTTP_LOGIN)
    Observable<LoginResultModel> doLogin(@Query("username") String username, @Query("password") String password);




}
