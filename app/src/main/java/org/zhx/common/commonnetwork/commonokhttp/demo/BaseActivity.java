package org.zhx.common.commonnetwork.commonokhttp.demo;

import android.content.Intent;
import android.os.Bundle;

import org.zhx.common.mvp.DialogApi;
import org.zhx.common.mvp.MvpActivity;

/**
 * Copyright (C), 2015-2020
 * FileName: BaseActivity
 * Author: zx
 * Date: 2020/1/22 11:28
 * Description:
 */
public abstract class BaseActivity extends MvpActivity {
    @Override
    public DialogApi creatLoadingDialog() {
        return new LoadingDialog(this);
    }
}
