package org.zhx.common.commonnetwork.commonokhttp.demo.mvp.presenters;

import org.zhx.common.commonnetwork.commonokhttp.demo.WeatherInfo;
import org.zhx.common.commonnetwork.commonokhttp.demo.mvp.views.WeatherApi;
import org.zhx.common.mvp.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C), 2015-2020
 * FileName: WeatherPresenter
 * Author: zx
 * Date: 2020/1/22 15:52
 * Description:
 */
public class WeatherPresenter extends BasePresenter<WeatherApi.view> implements WeatherApi.presenter {
    public WeatherPresenter(WeatherApi.view view) {
        super(view);
    }

    @Override
    public void getWeatherInfo() {
        manager.with(WeatherApi.class).getTest().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeatherInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WeatherInfo info) {
                mView.onWeatherInfo(info);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                mView.dismissLoadingDialog();
            }
        });
    }
}
