package com.ywp.nice.http.nohttp;

import com.google.gson.Gson;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * 自定义JavaBean请求
 * Created by ywp on 2017/1/11.
 */

public class JavaBeanRequest<T> extends RestRequest<T> {
    private Gson mGson = new Gson();
    Class<T> clazz;

    public JavaBeanRequest(String url, Class<T> clazz) {
        this(url, RequestMethod.GET, clazz);
    }

    public JavaBeanRequest(String url, RequestMethod requestMethod, Class<T> clazz) {
        super(url, requestMethod);
        this.clazz = clazz;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
        String response = StringRequest.parseResponseString(responseHeaders, responseBody);
        return mGson.fromJson(response, clazz);
    }


}
