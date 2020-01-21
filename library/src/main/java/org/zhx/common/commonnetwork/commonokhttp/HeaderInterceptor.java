package org.zhx.common.commonnetwork.commonokhttp;
import java.util.Map;

/**
 * Copyright (C), 2015-2020
 * FileName: HeaderInterceptor
 * Author: zx
 * Date: 2020/1/20 17:09
 * Description:
 */
public interface HeaderInterceptor {
    public Map<String,String> creatHeader();
}
