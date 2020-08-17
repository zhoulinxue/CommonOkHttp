package org.zhx.common.commonnetwork.customObservable;

import org.zhx.common.commonnetwork.api.CommonNetRequest;
import org.zhx.common.commonnetwork.api.CommonNetRequestCallBack;

import io.reactivex.Observable;

/**
 * @ProjectName: CommonOkHttp
 * @Package: org.zhx.common.commonnetwork.customObservable
 * @ClassName: CustomObservable
 * @Description:java类作用描述
 * @Author: zhouxue
 * @CreateDate: 2020/8/17 11:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 11:35
 * @UpdateRemark: 更新说明
 * @Version:1.0
 */
public abstract class CommonObservable<T> extends Observable<T> {
    private CommonOkExcutor mExcutor;
    private CommonNetRequest request;

    public CommonObservable() {
        this.mExcutor = new CommonOkExcutor(this);
    }

    public CommonObservable excute(CommonNetRequestCallBack<?, ?> netRequstAdapter) {
        if (mExcutor != null) {
            mExcutor.setNetRequstAdapter(netRequstAdapter);
            request = mExcutor.excute();
        }
        return this;
    }

    public void cancel() {
        if (request != null) {
            request.cancel();
        }
    }
}
