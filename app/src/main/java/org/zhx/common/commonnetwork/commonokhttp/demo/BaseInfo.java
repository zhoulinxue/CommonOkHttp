package org.zhx.common.commonnetwork.commonokhttp.demo;

import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.BaseData;

/**
 * Copyright (C), 2015-2020
 * FileName: BaseInfo
 * Author: zx
 * Date: 2020/1/22 16:53
 * Description:
 */
public class BaseInfo<T> implements BaseData<T> {
    private String message;
    private int code;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean isSuc() {
        return getCode() == 200;
    }

    @Override
    public String responeCode() {
        return getCode() + "";
    }

    @Override
    public T resultData() {
        return getData();
    }

    @Override
    public String message() {
        return getMessage();
    }
}
