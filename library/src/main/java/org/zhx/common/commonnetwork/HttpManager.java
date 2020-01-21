package org.zhx.common.commonnetwork;

import android.util.Log;

import org.zhx.common.commonnetwork.commonokhttp.OkConfig;

import java.io.IOException;
import java.util.HashMap;
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
    private OkConfig okConfig;
    private Retrofit.Builder defaultBuilder;
    private OkHttpClient mClient;
    private Map<String, Object> okhttpModel = new HashMap<>();

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
    public void init(OkConfig builder) {
        this.okConfig = builder;
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

    private OkHttpClient buildClient(final OkConfig config) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .sslSocketFactory(config.getSslContext().getSocketFactory(), config.getX509TrustManager())
                .hostnameVerifier(config.getHostnameVerifier())
                .cookieJar(config.getCookieJar())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Map<String, String> map = config.getInterceptor().initHeader();
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

    public <T> T with(Class<T> service) {
        if (service != null) {
            Object object = okhttpModel.get(service.getSimpleName());
            if (object == null) {
                Log.e(TAG, "creat  new Model" + service.getSimpleName());
                object = defaultBuilder.build().create(service);
                okhttpModel.put(service.getSimpleName(), object);
            }
            return (T) object;
        }
        return null;
    }

    public OkHttpClient getDefaultClient() {
        return mClient;
    }
}
