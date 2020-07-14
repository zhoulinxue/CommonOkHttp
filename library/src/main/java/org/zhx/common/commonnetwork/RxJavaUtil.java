package org.zhx.common.commonnetwork;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * pakage :com.gaea.modelnetwork.okhttp.rxjava
 * auther :zx
 * creatTime: 2019/7/29
 * description :
 */
public class RxJavaUtil {
    private static String TAG = RxJavaUtil.class.getSimpleName();

    public static <T> T rxbindMainEvent(final OnRxEvent<T> callback) {
        return rxbindSubcribeEvent(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                if (emitter != null) {
                    emitter.onNext(callback.onEvent());
                    emitter.onComplete();
                }
            }
        }, callback);
    }

    public static <T> T rxbindSubcribeEvent(ObservableOnSubscribe<T> subscribe, OnRxEvent<T> callback) {
        Observable.create(subscribe).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callback);
        return null;
    }

    public static <T> T rxbindSubcribeNewEvent(ObservableOnSubscribe<T> subscribe, OnRxEvent<T> callback) {
        Observable.create(subscribe).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callback);
        return null;
    }


    public static <T> T rxbindSubcribeEventSchedulers(ObservableOnSubscribe<T> subscribe, Observer<T> callback) {
        Observable.create(subscribe).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(callback);
        return null;
    }

    public static <T> T rxbindNewEvent(final OnRxEvent<T> callback) {
        return rxbindSubcribeNewEvent(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                if (emitter != null) {
                    emitter.onNext(callback.onEvent());
                    emitter.onComplete();
                }
            }
        }, callback);

    }

    /**
     * 查空
     */
    private static <T> void checkNoNull(T value) {
        if (value == null) {
            return;
        }
    }

    private static class ViewClickOnSubscribe implements ObservableOnSubscribe<View> {
        private View view;

        public ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(final ObservableEmitter<View> emitter) throws Exception {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //发送消息
                    if (emitter != null && !emitter.isDisposed() && v != null)
                        emitter.onNext(v);
                }
            });
        }
    }


    /**
     * 防止重复点击 * * @param target 目标view * @param action 监听器
     */
    public static void setOnClickListeners(final View.OnClickListener listener, @NonNull View... target) {
            setOnClickListeners(listener, 800, target);
    }

    /**
     * 防止重复点击 * * @param target 目标view * @param action 监听器
     */
    public static void setOnClickListeners(final View.OnClickListener listener, int time, @NonNull View... target) {
        for (View view : target) {
            onClick(view).throttleFirst(time, TimeUnit.MILLISECONDS).subscribe(new Observer<View>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(View view) {
                    listener.onClick(view);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    /**
     * 监听onclick事件防抖动 * * @param view * @return
     */
    @NonNull
    private static Observable<View> onClick(@NonNull View view) {
        checkNoNull(view);
        return Observable.create(new ViewClickOnSubscribe(view));
    }
}
