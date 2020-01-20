package org.zhx.common.commonnetwork.commonokhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Copyright (C), 2015-2020
 * FileName: HeadInterceptor
 * Author: zx
 * Date: 2020/1/20 17:09
 * Description:
 */
public interface HeadInterceptor {
    public Response onIntercepter(Interceptor.Chain chain) throws IOException;
}
