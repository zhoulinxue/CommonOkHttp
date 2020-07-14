package org.zhx.common.commonnetwork;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Copyright (C), 2015-2020
 * FileName: OkHttpFactory
 * Author: zx
 * Date: 2020/1/22 14:16
 * Description:
 */
public class OkHttpFactory {
    private org.zhx.common.commonnetwork.commonokhttp.OkConfig okConfig;
    private Retrofit.Builder builder;
    private String TAG = OkHttpFactory.class.getSimpleName();
    private Converter.Factory mConvertFactory;

    public OkHttpFactory(Converter.Factory mConvertFactory) {
        this.mConvertFactory = mConvertFactory;
    }

    /**
     * 初始化 client
     *
     * @param builder
     */
    public void creatBuilderFromCofig(org.zhx.common.commonnetwork.commonokhttp.OkConfig builder) {
        this.okConfig = builder;
        if (builder != null) {
            this.builder = creatNewBuilder(builder);
        } else {
            Log.e(TAG, "HttpManger creatBuilderFromCofig  failed...(commonOkBuilder can  not  be  null)");
        }
    }

    /**
     * 初始化 okhttp
     */
    protected Retrofit.Builder creatNewBuilder(org.zhx.common.commonnetwork.commonokhttp.OkConfig builder) {
        Retrofit.Builder defaultBuilder = new Retrofit.Builder();
        OkHttpClient client = builder.getClient() == null ? buildClient(builder) : builder.getClient();
        defaultBuilder.client(client);
        builder.setClient(client);
        if (!TextUtils.isEmpty(builder.getBaseUrl())) {
            defaultBuilder.baseUrl(builder.getBaseUrl());
        } else {
            defaultBuilder.baseUrl("https://www.baidu.com");
        }
        if (builder.getCallFactory() != null) {
            defaultBuilder.addCallAdapterFactory(builder.getCallFactory());
        } else {
            defaultBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        if (builder.getConverterFactory() != null) {
            defaultBuilder.addConverterFactory(builder.getConverterFactory());
        } else {
            defaultBuilder.addConverterFactory(mConvertFactory);
        }
        return defaultBuilder;
    }

    protected OkHttpClient buildClient(final org.zhx.common.commonnetwork.commonokhttp.OkConfig config) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor = config.getOkInterceptor() == null ? new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requstBuilder = chain.request().newBuilder();
                if (config.getInterceptor() == null) {
                    return chain.proceed(requstBuilder.build());
                }
                Map<String, String> map = config.getInterceptor().creatHeader();
                if (map != null)
                    for (String key : map.keySet()) {
                        Log.e(TAG, "header:  " + key + "=" + map.get(key));
                        requstBuilder.addHeader(key, map.get(key));
                    }
                return chain.proceed(requstBuilder.build());
            }
        } : config.getOkInterceptor();
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .cookieJar(config.getCookieJar())
                .addInterceptor(interceptor)
                .addInterceptor(logInterceptor);
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

    public org.zhx.common.commonnetwork.commonokhttp.OkConfig getOkConfig() {
        return okConfig;
    }
}
