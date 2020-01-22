package org.zhx.common.commonnetwork.commonokhttp.demo.bean;

import java.util.List;

/**
 * Copyright (C), 2015-2020
 * FileName: Extras
 * Author: zx
 * Date: 2020/1/21 18:02
 * Description:
 */
public class Extras {
    private String shidu;
    private String     pm25;
    private String  quality;
    private String    wendu;
    private String  ganmao;
    private List<ForecastInfo> forecast;

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public List<ForecastInfo> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastInfo> forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "Extras{\n" +
                "shidu='" + shidu + '\'' +
                ", \npm25='" + pm25 + '\'' +
                ", \nquality='" + quality + '\'' +
                ", \nwendu='" + wendu + '\'' +
                ", \nganmao='" + ganmao + '\'' +
                ", \nforecast=" + forecast +
                '}'+"\n";
    }
}
