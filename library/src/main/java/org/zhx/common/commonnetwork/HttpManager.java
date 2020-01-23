package org.zhx.common.commonnetwork;

import android.text.TextUtils;
import android.util.Log;

import org.zhx.common.commonnetwork.commonokhttp.OkConfig;

import java.util.HashMap;
import java.util.Map;

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
    private OkHttpFactory defaultFactory;
    private final String DEFAULT = "default";

    public static HttpManager getInstance() {
        if (manager == null) {
            synchronized (HttpManager.class) {
                manager = new HttpManager();
            }
        }
        return manager;
    }

    public HttpManager() {
        defaultFactory = new OkHttpFactory();
    }

    /**
     * 初始化 client
     *
     * @param builder
     */
    public void init(OkConfig builder) {
        if (defaultFactory != null) {
            defaultFactory.creatDefaultFromCofig(builder);
            builderMap.put(DEFAULT, defaultFactory);
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
            if (object == null) {
                Log.e(TAG, "creat  new Model" + service.getSimpleName());
                object = creatServerFromFactory(defaultFactory, service);
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
            return factory.getBuilder().build().create(service);
        }
        return null;
    }

    public OkHttpFactory getDefaultFactory() {
        return getOkFactorey(DEFAULT);
    }
}
