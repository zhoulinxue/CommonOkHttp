package org.zhx.common.commonnetwork;

import android.text.TextUtils;
import android.util.Log;

import org.zhx.common.commonnetwork.commonokhttp.OkConfig;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C), 2015-2020
 * FileName: OkHttpFactory
 * Author: zx
 * Date: 2020/1/22 14:16
 * Description:
 */
public class OkHttpFactory {
    private OkConfig okConfig;
    private Retrofit.Builder builder;
    private String TAG = OkHttpFactory.class.getSimpleName();

    public OkHttpFactory() {
        builder = new Retrofit.Builder();
    }
    /**
     * 初始化 client
     *
     * @param builder
     */
    public void creatDefaultFromCofig(OkConfig builder) {
        this.okConfig = builder;
        if (builder != null) {
            this.builder = creatNewBuilder(builder, "default");
        } else {
            Log.e(TAG, "HttpManger creatDefaultFromCofig  failed...(commonOkBuilder can  not  be  null)");
        }
    }
    /**
     * 初始化 okhttp
     */
    protected Retrofit.Builder creatNewBuilder(OkConfig builder, String tag) {
        Retrofit.Builder defaultBuilder = new Retrofit.Builder();
        defaultBuilder.addConverterFactory(GsonConverterFactory.create());
        defaultBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        OkHttpClient client = builder.getClient() == null ? buildClient(builder) : builder.getClient();
        defaultBuilder.client(client);
        if (!TextUtils.isEmpty(builder.getBaseUrl())) {
            defaultBuilder.baseUrl(builder.getBaseUrl());
        } else {
            defaultBuilder.baseUrl("https://www.baidu.com");
        }
        if (builder.getCallFactory() != null) {
            defaultBuilder.addCallAdapterFactory(builder.getCallFactory());
        }
        if (builder.getConverterFactory() != null) {
            defaultBuilder.addConverterFactory(builder.getConverterFactory());
        }
        return defaultBuilder;
    }

    protected OkHttpClient buildClient(final OkConfig config) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .cookieJar(config.getCookieJar())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder requstBuilder = chain.request().newBuilder();
                        Request request = requstBuilder.build();
                        if (config.getInterceptor() == null) {
                            return chain.proceed(request);
                        }
                        Map<String, String> map = config.getInterceptor().creatHeader();
                        if (map != null)
                            for (String key : map.keySet()) {
                                requstBuilder.addHeader(key, map.get(key));
                            }
                        return chain.proceed(request);
                    }
                });
        if (config.isHttps()) {
            builder.sslSocketFactory(config.getSslContext().getSocketFactory(), config.getX509TrustManager())
                    .hostnameVerifier(config.getHostnameVerifier());
        }
        OkHttpClient client = builder.build();
        return client;
    }

    public Retrofit.Builder getBuilder() {
        return builder;
    }

    public OkConfig getOkConfig() {
        return okConfig;
    }
}
