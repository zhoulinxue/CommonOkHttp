package org.zhx.common.commonnetwork.customObservable;

import org.zhx.common.commonnetwork.api.CommonNetRequest;
import org.zhx.common.commonnetwork.api.CommonNetRequestCallBack;

import java.util.List;

import io.reactivex.Observable;

/**
 * pakage :org.zhx.common.commonnetwork.commonokhttp.customObservable
 * auther :zx
 * creatTime: 2019/7/3
 * description :
 */
public class CommonObservable<T> {
    Observable<T> observable;
    private CommonOkExcutor mExcutor;
    private CommonNetRequest request;

    public CommonObservable(Observable<T> observable) {
        this.observable = observable;
        this.mExcutor = new CommonOkExcutor(observable);
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
