package org.zhx.common.commonnetwork.commonokhttp.demo;

import org.zhx.common.commonnetwork.commonokhttp.demo.bean.WeatherInfo;
import org.zhx.common.commonnetwork.customObservable.CommonObservable;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Copyright (C), 2015-2020
 * FileName: WeatherApi
 * Author: zx
 * Date: 2020/1/21 15:52
 * Description:
 */
public interface WeatherApi {
    @GET("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100")
    public Observable<WeatherInfo> getTest();


    @GET("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100")
    public CommonObservable<WeatherInfo> getCustomTest();

}
