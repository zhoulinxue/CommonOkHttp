package org.zhx.common.commonnetwork;

import org.zhx.common.commonnetwork.commonokhttp.CommonOkBuilder;

import retrofit2.Retrofit;

/**
 * Copyright (C), 2015-2020
 * FileName: HttpManager
 * Author: zx
 * Date: 2020/1/20 16:50
 * Description:
 */
public class HttpManager {
    private static HttpManager manager;
    private CommonOkBuilder builder;
    private Retrofit.Builder retrofitBuilder;
    public static HttpManager getInstance() {
        if (manager == null)
            synchronized (HttpManager.class) {
                manager = new HttpManager();
            }
        return manager;
    }

    public HttpManager() {
        retrofitBuilder =new Retrofit.Builder();
    }

    public void init(CommonOkBuilder builder) {
        this.builder = builder;
        if (builder != null) {

        }
    }
}
