package org.zhx.common.commonnetwork.commonokhttp.demo;

import java.util.List;

/**
 * Copyright (C), 2015-2020
 * FileName: WeatherInfo
 * Author: zx
 * Date: 2020/1/21 18:01
 * Description:
 */
public class WeatherInfo {
    private String message;
    private int status;
    private String date;
    private String time;
    private CityInfo cityInfo;
    private Extras data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public Extras getData() {
        return data;
    }

    public void setData(Extras data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WeatherInfo{\n" +
                "message='" + message + '\'' +
                ", \nstatus=" + status +
                ", \ndate='" + date + '\'' +
                ", \ntime='" + time + '\'' +
                ", \ncityInfo=" + cityInfo +
                ", \ndata=" + data +
                '}'+"\n";
    }
}
