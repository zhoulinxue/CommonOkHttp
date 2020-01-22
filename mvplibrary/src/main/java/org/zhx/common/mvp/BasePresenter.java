package org.zhx.common.mvp;

import org.zhx.common.commonnetwork.HttpManager;
import org.zhx.common.commonnetwork.OkHttpFactory;
import org.zhx.common.commonnetwork.commonokhttp.OkConfig;
import org.zhx.common.commonnetwork.commonokhttp.OkConfigBuilder;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.CommonCallAdapterFactory;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonNetRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2015-2020
 * FileName: BasePresenter
 * Author: zx
 * Date: 2020/1/22 11:47
 * Description:
 */
public abstract class BasePresenter<V extends BaseMvpView> {
    protected V mView;
    protected int pageNumber = 1;
    protected int pageSize = 10;
    protected int mTotalNum;
    protected int mTotalPage = 0;
    protected boolean hasMore;
    protected HttpManager manager;
    private boolean isNewBuilder = false;
    private OkHttpFactory factory;
    protected List<CommonNetRequest> mRequests = new ArrayList<>();

    public boolean isNewBuilder() {
        return isNewBuilder;
    }

    public BasePresenter(V view) {
        this.mView = view;
        creatNewHttpManager();
    }

    /**
     * @return
     */
    private void creatNewHttpManager() {
        manager = HttpManager.getInstance();
        OkConfig config = onCreatHttpCofig();
        manager.init(config);
        factory = creatNewFactory(config);
    }

    protected OkHttpFactory creatNewFactory(OkConfig config) {
//        OkHttpFactory factory = new OkHttpFactory();
//        factory.creatDefaultFromCofig(config);
        return manager.getDefaultFactory();
    }

    /**
     * 可根据 presenter  配置 适合 的 retrofit builder
     *
     * @return
     */
    protected OkConfig onCreatHttpCofig() {
        OkConfig config = new OkConfigBuilder()
                .setCallFactory(CommonCallAdapterFactory.create())
                .build();
        return config;
    }
}
