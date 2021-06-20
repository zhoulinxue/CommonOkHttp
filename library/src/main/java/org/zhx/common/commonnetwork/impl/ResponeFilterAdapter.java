package org.zhx.common.commonnetwork.impl;

import org.zhx.common.commonnetwork.api.ResponeFilter;

/**
 * @ProjectName: CommonMvp
 * @Package: org.zhx.common.commonnetwork.impl
 * @ClassName: ResponeFilterAdapter
 * @Description:java
 * @Author: zhouxue
 * @CreateDate: 2020/11/27 15:40
 * @UpdateUser:
 * @UpdateDate: 2020/11/27 15:40
 * @UpdateRemark:
 * @Version:1.0
 */
public class ResponeFilterAdapter implements ResponeFilter {
    @Override
    public boolean onError(String code, String message) {
        return false;
    }

    @Override
    public void onResult(Object object) {

    }
}
