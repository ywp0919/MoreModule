package com.ywp.nice.http.retrofitRx.api;

import com.ywp.nice.model.GankFuLiModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface GankApi {

    @GET("http://gank.io/api/data/福利/{size}" + "/" + "{page}")
    Observable<GankFuLiModel> loadGankPage(@Path("size") int size, @Path("page") int page);
}
