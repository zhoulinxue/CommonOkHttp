package org.zhx.common.mvp;

/**
 * pakage :company.yinu.android.mvp
 * auther :zx
 * creatTime: 2019/8/1
 * description :
 */
public abstract class ObjectNetRequstAdapter<R> extends NetRequstAdapter<R, R> {
    public ObjectNetRequstAdapter(BaseMvpView mvpView) {
        super(mvpView);
    }

    public ObjectNetRequstAdapter(BaseMvpView mvpView, boolean isShowToast) {
        super(mvpView, isShowToast);
    }

    @Override
    public boolean onResult(R info) {
        onResultData(info);
        return true;
    }

    protected abstract void onResultData(R info);

    @Override
    public void onData(R r) {

    }
}
