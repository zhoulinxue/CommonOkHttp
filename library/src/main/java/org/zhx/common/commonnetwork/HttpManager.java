package org.zhx.common.commonnetwork;

/**
 * Copyright (C), 2015-2020
 * FileName: HttpManager
 * Author: zx
 * Date: 2020/1/20 16:50
 * Description:
 */
public class HttpManager {
    private static HttpManager manager;
    public static HttpManager getInstance() {
        if (manager == null)
            synchronized (HttpManager.class) {
                manager = new HttpManager();
            }
        return manager;
    }
}
