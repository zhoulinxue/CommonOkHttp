package org.zhx.common.commonnetwork;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * pakage :com.gaea.modelnetwork.okhttp.rxjava
 * auther :zx
 * creatTime: 2019/7/29
 * description :
 */
public abstract class OnRxEvent<T> implements Observer<T> {
    private String TAG = OnRxEvent.class.getSimpleName();

    public abstract T onEvent();

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError " + e.getMessage());
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
