package org.zhx.common.commonnetwork;

import android.text.TextUtils;
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
    private Map<String, Object> okhttpModel = new HashMap<>();
    private Map<String, Retrofit.Builder> builderMap = new HashMap<>();

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
    public void creatDefaultFromCofig(OkConfig builder) {
        this.okConfig = builder;
        if (builder != null) {
            defaultBuilder = creatNewBuilder(builder, "default");
        } else {
            Log.e(TAG, "HttpManger creatDefaultFromCofig  failed...(commonOkBuilder can  not  be  null)");
        }
    }

    /**
     * 获取builder
     *
     * @param tag
     * @return
     */
    public Retrofit.Builder getRetrofitBuilder(String tag) {
        Retrofit.Builder defaultBuilder = null;
        if (!TextUtils.isEmpty(tag)) {
            defaultBuilder = builderMap.get(tag);
        }
        return defaultBuilder;
    }

    /**
     * 初始化 okhttp
     */
    public Retrofit.Builder creatNewBuilder(OkConfig builder, String tag) {
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
        builderMap.put(tag, defaultBuilder);
        return defaultBuilder;
    }

    public OkHttpClient buildClient(final OkConfig config) {
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

    /**
     * 获取 默认请求 配置 对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T with(Class<T> service) {
        if (service != null) {
            Object object = okhttpModel.get(service.getSimpleName());
            if (object == null) {
                Log.e(TAG, "creat  new Model" + service.getSimpleName());
                object = creatServer(defaultBuilder, service);
                okhttpModel.put(service.getSimpleName(), object);
            }
            return (T) object;
        }
        return null;
    }

    /**
     * 通过指定的 配置 获取 请求对象
     *
     * @param builder
     * @param service
     * @param <T>
     * @return
     */
    public <T> T creatServer(Retrofit.Builder builder, Class<T> service) {
        if (service != null && builder != null) {
            return builder.build().create(service);
        }
        return null;
    }

    public OkConfig getOkConfig() {
        return okConfig;
    }
}
