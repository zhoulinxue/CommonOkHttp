package org.zhx.common.mvp;

import android.content.Context;

/**
 * Copyright (C), 2015-2020
 * FileName: BaseMvpView
 * Author: zx
 * Date: 2020/1/22 10:35
 * Description:
 */
public interface BaseMvpView {
    void showToast(String msg);

    void showToast(int res);

    DialogApi creatLoadingDialog();

    void showLoadingDialog();

    void showLoadingDialog(int resId);

    void dismissLoadingDialog();

    void onError(String code, String msg);

    Context getContext();
}
