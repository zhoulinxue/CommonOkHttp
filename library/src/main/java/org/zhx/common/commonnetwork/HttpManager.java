package org.zhx.common.commonnetwork;

import android.text.TextUtils;
import android.util.Log;

import org.zhx.common.commonnetwork.customObservable.CommonCallAdapterFactory;
import org.zhx.common.commonnetwork.customObservable.CommonObservable;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

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
    private Map<String, Object> okhttpModel = new HashMap<>();
    private Map<String, OkHttpFactory> builderMap = new HashMap<>();
    public static String DEFAULT_TAG = "HttpManager";
    private Converter.Factory mConvertFactory;

    public static HttpManager getInstance() {
        if (manager == null) {
            synchronized (HttpManager.class) {
                manager = new HttpManager();
            }
        }
        return manager;
    }

    public HttpManager() {
    }

    /**
     * 初始化 client
     */
    public void init(Converter.Factory convertFactory) {
        this.mConvertFactory = convertFactory;
        OkConfig builder = new OkConfigBuilder(DEFAULT_TAG)
                .setCallFactory(CommonCallAdapterFactory.create())
                .setConverterFactory(convertFactory)
                .setHttps(true)
                .build();
        initFactoryByTag(builder);
    }

    /**
     * 初始化 client
     *
     * @param okConfig
     */
    public void initFactoryByTag(OkConfig okConfig) {
        OkHttpFactory factory = builderMap.get(okConfig.getBuilderTag());
        if (factory == null) {
            factory = creatNewFactory(okConfig);
            builderMap.put(okConfig.getBuilderTag(), factory);
        }
    }

    public OkHttpFactory creatNewFactory(OkConfig okConfig) {
        OkHttpFactory factory = new OkHttpFactory(mConvertFactory);
        factory.creatBuilderFromCofig(okConfig);
        return factory;
    }

    /**
     * 获取builder
     *
     * @param tag
     * @return
     */
    public OkHttpFactory getOkFactorey(String tag) {
        OkHttpFactory builder = null;
        if (!TextUtils.isEmpty(tag)) {
            builder = builderMap.get(tag);
        }
        return builder;
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
            OkHttpFactory factory = builderMap.get(service.getSimpleName());
            if (object == null) {
                Log.e(TAG, "creat  new Model" + service.getSimpleName());
                if (factory == null) {
                    factory = getDefaultFactory();
                }
                object = creatServerFromFactory(factory, service);
                okhttpModel.put(service.getSimpleName(), object);
            }
            return (T) object;
        }
        return null;
    }

    /**
     * 获取 默认请求 配置 对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T with(Class<T> service, OkConfig config) {
        OkHttpFactory factory = null;
        if (config != null) {
            factory = builderMap.get(config.getBuilderTag());
            if (factory == null) {
                factory = creatNewFactory(config);
                builderMap.put(config.getBuilderTag(), factory);
            }
        }
        if (service != null) {
            T object = (T) okhttpModel.get(service.getSimpleName());
            if (object == null) {
                Log.e(TAG, "creat  new Model" + service.getSimpleName());
                object = creatServerFromFactory(factory, service);
            }
            return (T) object;
        }
        return null;
    }

    /**
     * 通过指定的 配置 获取 请求对象
     *
     * @param factory
     * @param service
     * @param <T>
     * @return
     */
    public <T> T creatServerFromFactory(OkHttpFactory factory, Class<T> service) {
        if (service != null && factory != null) {
            Retrofit.Builder builder = factory.getBuilder();
            if (builder == null) {
                builder = getDefaultFactory().getBuilder();
            }
            return builder.build().create(service);
        }
        return null;
    }

    public OkHttpFactory getDefaultFactory() {
        return getOkFactorey(DEFAULT_TAG);
    }
}
