package org.zhx.common.commonnetwork.commonokhttp.demo;

import org.zhx.common.commonnetwork.commonokhttp.demo.bean.WeatherInfo;

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
    @GET("http://t.weather.sojson.com/api/weather/city/101030100")
    public Observable<WeatherInfo> getTest();

}
