package org.zhx.common.commonnetwork.commonokhttp;

import android.content.Context;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Copyright (C), 2015-2020
 * FileName: CommonOkBuilder
 * Author: zx
 * Date: 2020/1/20 16:59
 * Description:
 */
public class CommonOkBuilder {
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

    public OkHttpClient getClient() {
        return mClient;
    }

    public CommonOkBuilder setClient(OkHttpClient mClient) {
        this.mClient = mClient;
        return this;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public CommonOkBuilder setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        return this;
    }

    public CommonOkBuilder() {
        x509TrustManager = x509();
        hostnameVerifier = hostVerifier();
        cookieJar = cookiejar();
    }

    private CookieJar cookiejar() {
        return new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
    }

    private HostnameVerifier hostVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    private X509TrustManager x509() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
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

    public HeaderInterceptor getInterceptor() {
        return interceptor;
    }

    public CommonOkBuilder setInterceptor(HeaderInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public CommonOkBuilder setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public CommonOkBuilder setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public CommonOkBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public X509TrustManager getX509TrustManager() {
        return x509TrustManager;
    }

    public CommonOkBuilder setX509TrustManager(X509TrustManager x509TrustManager) {
        this.x509TrustManager = x509TrustManager;
        return this;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public CommonOkBuilder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public CookieJar getCookieJar() {
        return cookieJar;
    }

    public CommonOkBuilder setCookieJar(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
        return this;
    }
}
