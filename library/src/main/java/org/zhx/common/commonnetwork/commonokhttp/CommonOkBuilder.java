package org.zhx.common.commonnetwork.commonokhttp;

import android.content.Context;

import javax.net.ssl.SSLContext;

/**
 * Copyright (C), 2015-2020
 * FileName: CommonOkBuilder
 * Author: zx
 * Date: 2020/1/20 16:59
 * Description:
 */
public class CommonOkBuilder {
    private HeadInterceptor interceptor;
    private Context context;
    private String mBaseUrl;
    private SSLContext sslContext;

    public SSLContext getSslContext() {
        return sslContext;
    }

    public CommonOkBuilder setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        return this;
    }

    public CommonOkBuilder() {
    }

    public CommonOkBuilder(Context context) {
        this.context = context;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public CommonOkBuilder setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public CommonOkBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public HeadInterceptor getInterceptor() {
        return interceptor;
    }

    public CommonOkBuilder setInterceptor(HeadInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }


}
