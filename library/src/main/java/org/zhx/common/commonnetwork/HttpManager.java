package org.zhx.common.commonnetwork;

import android.text.TextUtils;
import android.util.Log;

import org.zhx.common.commonnetwork.commonokhttp.OkConfig;
import org.zhx.common.commonnetwork.commonokhttp.OkConfigBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Converter;
import retrofit2.Retrofit;

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
    private Class defaultTag;
    private Converter.Factory  mConvertFactory;
    public void setDefaultTag(Class defaultTag) {
        this.defaultTag = defaultTag;
    }

    public static HttpManager getInstance() {
        if (manager == null) {
            synchronized (HttpManager.class) {
                manager = new HttpManager();
            }
        }
        return manager;
    }

    public HttpManager() {
        defaultTag = HttpManager.class;
    }

    /**
     * 初始化 client
     */
    public void init(Converter.Factory  convertFactory) {
        this.mConvertFactory=convertFactory;
        OkConfig builder = new OkConfigBuilder(defaultTag).build();
        initFactoryByTag(builder);
    }

    /**
     * 初始化 client
     *
     * @param builder
     */
    public void initFactoryByTag(OkConfig builder) {
        OkHttpFactory factory = builderMap.get(builder.getBuilderTag().getSimpleName());
        if (factory == null) {
            factory = new OkHttpFactory(mConvertFactory);
            factory.creatBuilderFromCofig(builder);
            builderMap.put(builder.getBuilderTag().getSimpleName(), factory);
        }
    }

    /**
     * 获取builder
     *
     * @param tag
     * @return
     */
    public OkHttpFactory getOkFactorey(String tag) {
        OkHttpFactory defaultBuilder = null;
        if (!TextUtils.isEmpty(tag)) {
            defaultBuilder = builderMap.get(tag);
        }
        return defaultBuilder;
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
        return getOkFactorey(defaultTag.getSimpleName());
    }
}
