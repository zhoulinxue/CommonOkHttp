package org.zhx.common.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright (C), 2015-2020
 * FileName: BaseActivity
 * Author: zx
 * Date: 2020/1/22 10:34
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseMvpView {
    private DialogApi mLoading;

    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoading = creatLoadingDialog();
        int layout = initLayout();
        if (layout != 0) {
            setContentView(layout);
        }
        if (getIntent() != null)
            onLoadIntentData(getIntent());
        onCreatView();
        if (savedInstanceState != null)
            onLoadDataFromSavedInstanceState(savedInstanceState);
        onLoadDataFormNetWork();
    }

    @Override
    protected final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onLoadIntentData(intent);
    }

    /**
     * 初始化布局
     *
     * @return
     */
    protected abstract int initLayout();

    /**
     * 获取传递参数
     *
     * @param intent
     */
    protected abstract void onLoadIntentData(Intent intent);

    /**
     * 从 savedInstanceState 中获取 数据
     *
     * @param savedInstanceState
     */
    protected abstract void onLoadDataFromSavedInstanceState(Bundle savedInstanceState);

    /**
     * 布局已设置完
     */
    protected abstract void onCreatView();

    /**
     * 加载 网络数据
     */
    protected abstract void onLoadDataFormNetWork();


    @Override
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showToast(int res) {
        if (res != 0) {
            showToast(getResources().getString(res));
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mLoading != null && !mLoading.isShowing()) {
            mLoading.showLoading();
        }
    }

    @Override
    public void showLoadingDialog(int resId) {
        if (mLoading != null) {
            if (resId != 0)
                mLoading.setMessage(getResources().getString(resId));
            showLoadingDialog();
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismissloading();
        }
    }

    @Override
    public void onError(int code, String msg) {
        showToast(msg);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
