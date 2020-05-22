package org.zhx.common.commonnetwork.commonokhttp.customObservable.api;

import java.util.List;

/**
 * Copyright (C),zhx_2018
 * FileName: CommonNetRequestCallBack
 * Author: zhx
 * Date: 2018\10\31 0031 17:29
 * Description: ${DESCRIPTION}
 */
public interface CommonNetRequestCallBack<R,T> {
    public void onLoadComplete();

    public void onData(T t);

    public void onError(String responseCode, String msg);

    public boolean onResult(R info);

    public List<CommonNetRequest> getRequestList();

}
