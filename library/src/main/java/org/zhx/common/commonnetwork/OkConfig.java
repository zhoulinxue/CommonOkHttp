package org.zhx.common.commonnetwork;

import android.content.Context;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Copyright (C), 2015-2020
 * FileName: OkConfig
 * Author: zx
 * Date: 2020/1/21 11:17
 * Description:
 */
public class OkConfig {
    private static final int DEFAULT_TIME = 15;
    private HeaderInterceptor interceptor;
    private Context context;
    private String mBaseUrl;
    private SSLContext sslContext;
    private OkHttpClient mClient;
    private X509TrustManager x509TrustManager;
    private int connectTimeout = DEFAULT_TIME;
    private int writeTimeout = DEFAULT_TIME;
    private int readTimeout = DEFAULT_TIME;
    private HostnameVerifier hostnameVerifier;
    private CookieJar cookieJar;
    private Converter.Factory converterFactory;
    private CallAdapter.Factory callFactory;
    private boolean isHttps = true;
    private String buildName;
    private String builderTag;
    private Interceptor OkInterceptor;

    public String getBuilderTag() {
        return builderTag;
    }

    public OkConfig setBuilderTag(String builderTag) {
        this.builderTag = builderTag;
        return this;
    }
    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public CallAdapter.Factory getCallFactory() {
        return callFactory;
    }

    public void setCallFactory(CallAdapter.Factory callFactory) {
        this.callFactory = callFactory;
    }

    public void setConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public HeaderInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(HeaderInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public OkHttpClient getClient() {
        return mClient;
    }

    public void setClient(OkHttpClient mClient) {
        this.mClient = mClient;
    }

    public X509TrustManager getX509TrustManager() {
        return x509TrustManager;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.x509TrustManager = x509TrustManager;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public CookieJar getCookieJar() {
        return cookieJar;
    }

    public void setCookieJar(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    public void setHttps(boolean https) {
        isHttps = https;
    }

    public Interceptor getOkInterceptor() {
        return OkInterceptor;
    }

    public OkConfig setOkInterceptor(Interceptor okInterceptor) {
        OkInterceptor = okInterceptor;
        return this;
    }

    public boolean isHttps() {
        return isHttps;
    }
}
