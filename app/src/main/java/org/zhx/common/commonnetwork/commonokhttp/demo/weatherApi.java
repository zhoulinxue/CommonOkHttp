package org.zhx.common.commonnetwork.commonokhttp.demo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Copyright (C), 2015-2020
 * FileName: weatherApi
 * Author: zx
 * Date: 2020/1/21 15:52
 * Description:
 */
public interface weatherApi {
    @GET("http://t.weather.sojson.com/api/weather/city/101030100")
    public Observable<WeatherInfo> getTest();
}
