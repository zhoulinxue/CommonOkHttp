package org.zhx.common.commonnetwork.impl;

import org.zhx.common.commonnetwork.api.CommonNetRequestCallBack;

public abstract class SimpleCommonCallBackAdapter<R> implements CommonNetRequestCallBack<R, Void> {
    public abstract void onResultData(R info);

    @Override
    public final void onData(Void aVoid) {

    }

    @Override
    public final boolean onResult(R info) {
        onResultData(info);
        return true;
    }
}
