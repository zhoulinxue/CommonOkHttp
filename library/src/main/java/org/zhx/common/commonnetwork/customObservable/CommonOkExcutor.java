package org.zhx.common.commonnetwork.customObservable;

import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonExcutable;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonNetRequest;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonNetRequestCallBack;

import java.util.List;

import io.reactivex.Observable;

/**
 * Copyright (C), 2015-2019
 * FileName: CommonOkExcutor
 * Author: zx
 * Date: 2019/12/18 14:37
 * Description:
 */
public class CommonOkExcutor<T> implements CommonExcutable {
    Observable<T> observable;
    private List<CommonNetRequest> mRequests;
    private CommonNetRequestCallBack<?, ?> netRequstAdapter;

    public CommonOkExcutor(Observable<T> observable) {
        this.observable = observable;
    }

    @Override
    public void excute() {
        new CommonOkHttpRequest<>(observable, netRequstAdapter).start(mRequests);
    }

    public void setRequests(List<CommonNetRequest> mRequests) {
        this.mRequests = mRequests;
    }

    public void setNetRequstAdapter(CommonNetRequestCallBack<?, ?> netRequstAdapter) {
        this.netRequstAdapter = netRequstAdapter;
    }
}
