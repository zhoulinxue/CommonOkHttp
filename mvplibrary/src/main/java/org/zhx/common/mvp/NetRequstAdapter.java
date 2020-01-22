package org.zhx.common.mvp;


import android.util.Log;

import org.zhx.common.commonnetwork.commonokhttp.NetWorkUtil;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonLocalError;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonNetRequestCallBack;

/**
 * pakage :com.gaea.kiki.mvp
 * auther :zx
 * creatTime: 2019/6/28
 */
abstract class NetRequstAdapter<R, T> implements CommonNetRequestCallBack<R, T> {
    private BaseMvpView mvpView;
    private boolean isShowToast = false;
    private boolean isDismissDialog = true;

    public NetRequstAdapter(BaseMvpView mvpView, boolean isShowToast) {
        this(mvpView);
        this.isShowToast = isShowToast;
    }

    public NetRequstAdapter(BaseMvpView mvpView, boolean isShowToast, boolean isDismissDialog) {
        this(mvpView);
        this.isShowToast = isShowToast;
        this.isDismissDialog = isDismissDialog;
    }

    public NetRequstAdapter(boolean isDismissDialog, BaseMvpView mvpView) {
        this(mvpView);
        this.isDismissDialog = isDismissDialog;
    }

    public NetRequstAdapter(BaseMvpView mvpView) {
        this.mvpView = mvpView;
        if (isShowToast) {
            if (NetWorkUtil.checkNetWorkStatus(mvpView.getContext())) {
                onError("-1", CommonLocalError.BAD_NETWORK.getErrorMsg());
            }
        }
    }

    @Override
    public void onLoadComplete() {
        if (mvpView != null && isDismissDialog) {
            mvpView.dismissLoadingDialog();
        }
    }

    @Override
    public void onError(String responseCode, String msg) {
        Log.e("OkHttpRequest", "onError..NetRequstAdapter...");
        if (mvpView != null) {
            mvpView.onError(responseCode + "", msg);
        }
    }
}
