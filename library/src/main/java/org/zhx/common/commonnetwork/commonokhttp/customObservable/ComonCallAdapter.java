package org.zhx.common.commonnetwork.commonokhttp.customObservable;

import androidx.annotation.Nullable;

import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;


/**
 * pakage :org.zhx.common.commonnetwork.commonokhttp.customObservable
 * auther :zx
 * creatTime: 2019/7/2
 * description :
 */
public class ComonCallAdapter<R> implements CallAdapter<R, Object> {
    private final Type responseType;
    private final @Nullable
    Scheduler scheduler;
    private final boolean isAsync;
    private final boolean isResult;
    private final boolean isBody;
    private final boolean isFlowable;
    private final boolean isSingle;
    private final boolean isMaybe;
    private final boolean isCompletable;
    Observable<?> observable;
    CommonObservable<?> commonObservable;

    ComonCallAdapter(Type responseType, @Nullable Scheduler scheduler, boolean isAsync,
                     boolean isResult, boolean isBody, boolean isFlowable, boolean isSingle, boolean isMaybe,
                     boolean isCompletable) {
        this.responseType = responseType;
        this.scheduler = scheduler;
        this.isAsync = isAsync;
        this.isResult = isResult;
        this.isBody = isBody;
        this.isFlowable = isFlowable;
        this.isSingle = isSingle;
        this.isMaybe = isMaybe;
        this.isCompletable = isCompletable;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<R> call) {
        Observable<Response<R>> responseObservable = new CommonCallExecuteObservable<>(call);
        if (isResult) {
            observable = new CommonResultObservable<>(responseObservable);
        } else if (isBody) {
            observable = new CommonBodyObservable<>(responseObservable);
        } else {
            observable = responseObservable;
        }
        commonObservable = new CommonObservable(observable);
        if (scheduler != null) {
            observable = observable.subscribeOn(scheduler);
        }

        if (isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (isSingle) {
            return observable.singleOrError();
        }
        if (isMaybe) {
            return observable.singleElement();
        }
        if (isCompletable) {
            return observable.ignoreElements();
        }
        return commonObservable;
    }
}
