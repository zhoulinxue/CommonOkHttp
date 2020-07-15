package org.zhx.common.commonnetwork.api;

/**
 * Copyright (C), 2015-2020
 * FileName: BaseData
 * Author: zx
 * Date: 2020/1/22 16:44
 * Description:
 */
public interface BaseData<T> {

    public boolean isSuc();

    public String responeCode();

    public T resultData();

    public String message();
}
