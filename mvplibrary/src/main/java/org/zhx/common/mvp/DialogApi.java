package org.zhx.common.mvp;

/**
 * Copyright (C), 2015-2020
 * FileName: DialogApi
 * Author: zx
 * Date: 2020/1/22 10:37
 * Description:
 */
public interface DialogApi {
    public void showLoading();

    public void dismissloading();

    public boolean isShowing();

    public void setMessage(String message);
}
