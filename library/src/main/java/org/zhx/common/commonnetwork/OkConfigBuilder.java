package org.zhx.common.commonnetwork;

import android.content.Context;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Copyright (C), 2015-2020
 * FileName: OkConfigBuilder
 * Author: zx
 * Date: 2020/1/20 16:59
 * Description:
 */
public class OkConfigBuilder {
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
    private String builderTag;

    public OkConfigBuilder(String builderTag) {
        this.builderTag = builderTag;
        x509TrustManager = x509();
        hostnameVerifier = hostVerifier();
        cookieJar = cookiejar();
        sslContext();
    }

    public String getBuilderTag() {
        return builderTag;
    }

    public OkConfigBuilder setCallFactory(CallAdapter.Factory callFactory) {
        this.callFactory = callFactory;
        return this;
    }

    public OkConfigBuilder setConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public OkConfigBuilder setClient(OkHttpClient mClient) {
        this.mClient = mClient;
        return this;
    }

    public OkConfigBuilder setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        return this;
    }

    private void sslContext() {
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{x509TrustManager},
                    new SecureRandom());
        } catch (Exception e) {
        }
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

    public OkConfigBuilder(Context context) {
        this.context = context;
    }

    public OkConfigBuilder setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
        return this;
    }

    public OkConfigBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public OkConfigBuilder setInterceptor(HeaderInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public OkConfigBuilder setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public OkConfigBuilder setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public OkConfigBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public OkConfigBuilder setX509TrustManager(X509TrustManager x509TrustManager) {
        this.x509TrustManager = x509TrustManager;
        return this;
    }

    public OkConfigBuilder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public OkConfigBuilder setCookieJar(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
        return this;
    }

    public OkConfigBuilder setHttps(boolean https) {
        isHttps = https;
        return this;
    }

    public org.zhx.common.commonnetwork.commonokhttp.OkConfig build() {
        OkConfig config = new OkConfig();
        config.setBaseUrl(mBaseUrl);
        config.setContext(context);
        config.setClient(mClient);

        config.setCookieJar(cookieJar);
        config.setInterceptor(interceptor);
        config.setHostnameVerifier(hostnameVerifier);
        config.setSslContext(sslContext);
        config.setX509TrustManager(x509TrustManager);
        config.setHttps(isHttps);

        config.setConnectTimeout(connectTimeout);
        config.setReadTimeout(readTimeout);
        config.setWriteTimeout(writeTimeout);

        config.setCallFactory(callFactory);
        config.setConverterFactory(converterFactory);
        config.setBuilderTag(getBuilderTag());
        return config;
    }
}
