package org.zhx.common.commonnetwork;

import android.util.Log;

import org.zhx.common.commonnetwork.commonokhttp.CommonOkBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Copyright (C), 2015-2020
 * FileName: HttpManager
 * Author: zx
 * Date: 2020/1/20 16:50
 * Description:
 */
public class HttpManager {
    private String TAG = HttpManager.class.getSimpleName();
    private static HttpManager manager;
    private CommonOkBuilder builder;
    private Retrofit.Builder defaultBuilder;
    private OkHttpClient mClient;

    public static HttpManager getInstance() {
        if (manager == null) {
            synchronized (HttpManager.class) {
                manager = new HttpManager();
            }
        }
        return manager;
    }

    public HttpManager() {
        defaultBuilder = new Retrofit.Builder();
    }

    /**
     * 初始化 client
     *
     * @param builder
     */
    public void init(CommonOkBuilder builder) {
        this.builder = builder;
        if (builder != null) {
            mClient = builder.getClient() == null ? buildClient(builder) : builder.getClient();
            defaultBuilder = creatNewBuilder(mClient);
            defaultBuilder.baseUrl(builder.getBaseUrl());
        } else {
            Log.e(TAG, "HttpManger init  failed  commonOkBuilder can  not  be  null.....");
        }
    }


    /**
     * 初始化 okhttp
     */
    protected Retrofit.Builder creatNewBuilder(OkHttpClient client) {
        Retrofit.Builder defaultBuilder = new Retrofit.Builder();
        defaultBuilder.addConverterFactory(GsonConverterFactory.create());
        defaultBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        defaultBuilder.client(client);
        return defaultBuilder;
    }

    private OkHttpClient buildClient(final CommonOkBuilder builder) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .connectTimeout(builder.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(builder.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(builder.getReadTimeout(), TimeUnit.SECONDS)
                .sslSocketFactory(builder.getSslContext().getSocketFactory(), builder.getX509TrustManager())
                .hostnameVerifier(builder.getHostnameVerifier())
                .cookieJar(builder.getCookieJar())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Map<String, String> map = builder.getInterceptor().initHeader();
                        Request.Builder requstBuilder = chain.request().newBuilder();
                        if (map != null)
                            for (String key : map.keySet()) {
                                requstBuilder.addHeader(key, map.get(key));
                            }
                        Request request = requstBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return client;
    }

    public OkHttpClient getDefaultClient() {
        return mClient;
    }
}
