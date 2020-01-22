package org.zhx.common.commonnetwork.commonokhttp.demo.bean;

/**
 * Copyright (C), 2015-2020
 * FileName: ForecastInfo
 * Author: zx
 * Date: 2020/1/21 18:05
 * Description:
 */
public class ForecastInfo {
    private String notice;
    private String type;
    private String  fl;
    private String fx;
    private String  aqi;
    private String  sunset;
    private String sunrise;
    private String week;
    private String  ymd;
    private String low;
    private String  high;
    private String date;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ForecastInfo{\n" +
                "notice='" + notice + '\'' +
                ", \ntype='" + type + '\'' +
                ", \nfl='" + fl + '\'' +
                ", \nfx='" + fx + '\'' +
                ", \naqi='" + aqi + '\'' +
                ", \nsunset='" + sunset + '\'' +
                ", \nsunrise='" + sunrise + '\'' +
                ", \nweek='" + week + '\'' +
                ", \nymd='" + ymd + '\'' +
                ", \nlow='" + low + '\'' +
                ", \nhigh='" + high + '\'' +
                ", \ndate='" + date + '\'' +
                '}'+"\n";
    }
}
