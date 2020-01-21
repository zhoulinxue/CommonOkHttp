package org.zhx.common.commonnetwork.commonokhttp.demo;

/**
 * Copyright (C), 2015-2020
 * FileName: CityInfo
 * Author: zx
 * Date: 2020/1/21 18:01
 * Description:
 */
public class CityInfo {
    private String city;
    private String citykey;
    private String parent;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitykey() {
        return citykey;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "CityInfo{\n" +
                "city='" + city + '\'' +
                ", \ncitykey='" + citykey + '\'' +
                ", \nparent='" + parent + '\'' +
                '}'+"\n";
    }
}
