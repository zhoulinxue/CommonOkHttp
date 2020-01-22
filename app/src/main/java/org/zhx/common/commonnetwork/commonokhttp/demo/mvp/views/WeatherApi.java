package org.zhx.common.commonnetwork.commonokhttp.demo.mvp.views;

import org.zhx.common.commonnetwork.commonokhttp.customObservable.CommonObservable;
import org.zhx.common.commonnetwork.commonokhttp.demo.WeatherInfo;
import org.zhx.common.mvp.BaseMvpView;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Copyright (C), 2015-2020
 * FileName: WeatherApi
 * Author: zx
 * Date: 2020/1/21 15:52
 * Description:
 */
public interface WeatherApi {
    @GET("http://t.weather.sojson.com/api/weather/city/101030100")
    public CommonObservable<WeatherInfo> getTest();

    public interface view extends BaseMvpView{
        void onWeatherInfo(WeatherInfo info);
    }
    public interface presenter {
        void getWeatherInfo();
    }
}
