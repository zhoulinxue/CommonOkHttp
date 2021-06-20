package org.zhx.common.commonnetwork;

import org.zhx.common.commonnetwork.api.ResponeFilter;

/**
 * @ProjectName: CommonOkHttp
 * @Package: org.zhx.common.commonnetwork
 * @ClassName: CommonOkHttp
 * @Description:java
 * @Author: 86138
 * @CreateDate: 2020/11/28 9:27
 * @UpdateUser:
 * @UpdateDate: 2020/11/28 9:27
 * @UpdateRemark:
 * @Version:1.0
 */
public class CommonOkHttp {
    public static ResponeFilter mFilter;

    public static void init(ResponeFilter filter) {
        mFilter = filter;
    }
}
