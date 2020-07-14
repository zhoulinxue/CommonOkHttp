package org.zhx.common.commonnetwork.api;

import java.util.List;

/**
 * Copyright (C), 2015-2018
 * FileName: CommonNetRequest
 * Author: zhx
 * Date: 2018\10\23 0023 19:55
 * Description: ${DESCRIPTION}
 */
public interface CommonNetRequest {
    public void cancel();

    public CommonNetRequest start(List<CommonNetRequest> requestList);

    public CommonNetRequest start();

}
