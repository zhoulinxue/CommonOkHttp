package org.zhx.common.commonnetwork.commonokhttp.customObservable;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.BaseData;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonLocalError;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonNetRequest;
import org.zhx.common.commonnetwork.commonokhttp.customObservable.api.CommonNetRequestCallBack;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * Name: CommonOkHttpRequest
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-14 11:28
 */
public class CommonOkHttpRequest<R, T> implements CommonNetRequest {
    private final String TAG = CommonOkHttpRequest.class.getSimpleName();
    private Observable mObservable;
    private CommonNetRequestCallBack<R, T> mCallback;
    private CompositeDisposable composite = new CompositeDisposable();

    public CommonOkHttpRequest(Observable mObservable, CommonNetRequestCallBack<R, T> mCallback) {
        this.mObservable = mObservable;
        this.mCallback = mCallback;
    }

    @Override
    public void cancel() {
        mCallback = null;
        if (composite != null && !composite.isDisposed()) {
            composite.dispose();
            composite.clear();
        }
    }

    @Override
    public CommonNetRequest start(List<CommonNetRequest> requestList) {
        mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
        if (requestList != null) {
            requestList.add(this);
        }
        return this;
    }

    private void add(List<CommonNetRequest> request) {
        Log.e(TAG, "add..");
        if (request != null && !request.contains(this)) {
            Log.e(TAG, "add..request");
            request.add(this);
        }
    }


    @Override
    public CommonNetRequest start() {
        mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
        if (mCallback != null)
            add(mCallback.getRequestList());
        return this;
    }

    Observer<R> mObserver = new Observer<R>() {

        @Override
        public void onSubscribe(Disposable d) {
            composite.add(d);
        }

        @Override
        public void onNext(R tBaseBean) {
            //TODO 按 泛型 解析数据
            if (mCallback != null) {
                boolean isBreak = mCallback.onResult(tBaseBean);
                if (!isBreak) {
                    boolean interf = isInterface(tBaseBean.getClass(), BaseData.class.getName());
                    if (interf) {
                        BaseData<T> baseData = (BaseData<T>) tBaseBean;
                        if (baseData.isSuc()) {
                            mCallback.onData(baseData.resultData());
                        } else {
                            mCallback.onError(baseData.responeCode(), baseData.message());
                        }
                    }
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError....");
            if (mCallback != null) {
                mCallback.onLoadComplete();
            }
            CommonLocalError error = null;
            String msg = e.getMessage();
            try {
                if (e instanceof HttpException) {     //   HTTP错误
                    error = CommonLocalError.BAD_NETWORK;
                    if (!TextUtils.isEmpty(msg) && msg.contains("HTTP 500")) {
                        error = CommonLocalError.ROMOTE_ERROR;
                    } else if (!TextUtils.isEmpty(msg) && msg.contains("HTTP 404")) {
                        error = CommonLocalError.ROMOTE_NOT_FOUND;
                    } else if (!TextUtils.isEmpty(msg) && msg.contains("HTTP 405")) {
                        error = CommonLocalError.METHOD_NOT_ALLOW;
                    } else {
                        error = CommonLocalError.CONNECT_ERROR;
                    }
                } else if (e instanceof IllegalArgumentException) {
                    if (!TextUtils.isEmpty(msg) && msg.contains("Malformed URL")) {
                        error = CommonLocalError.URL_NOT_FOUND;
                    }else {
                        error = CommonLocalError.ILLEGAL_ARGUMENT;
                    }
                } else if (e instanceof ConnectException
                        || e instanceof UnknownHostException) {   //   连接错误
                    error = CommonLocalError.CONNECT_ERROR;
                } else if (e instanceof InterruptedIOException) { //  连接超时
                    error = CommonLocalError.CONNECT_TIMEOUT;
                } else if (e instanceof JSONException
                        || e instanceof NumberFormatException
                        || e instanceof ParseException) {   //  解析错误
                    error = CommonLocalError.PARSE_ERROR;
                } else if(e instanceof NullPointerException&&msg.contains("Null is not a valid element")){
                    error = CommonLocalError.NULL_RESPONE;
                }else {
                    error = CommonLocalError.UNKNOWN_LOCAL_ERROR;
                }
            } catch (Exception e1) {
                Log.d("okhttp", "判断错误");
            } finally {
                e.printStackTrace();
            }
            if (error == null) {
                error = CommonLocalError.UNKNOWN_LOCAL_ERROR;
            }
            if (mCallback != null) {
                if (!"null".equals(error.getErrorMsg()) && !TextUtils.isEmpty(error.getErrorMsg()))
                    mCallback.onError(error.getErrorCode() + "", error.getErrorMsg() + "");
            } else {
                Log.e(TAG, "mCallback==null:" + (mCallback == null));
            }
        }

        @Override
        public void onComplete() {
            Log.e(TAG, "onComplete....");
            if (mCallback != null) {
                mCallback.onLoadComplete();
            }
        }
    };

    public boolean isInterface(Class c, String szInterface) {
        Class[] face = c.getInterfaces();
        for (int i = 0, j = face.length; i < j; i++) {
            if (face[i].getName().equals(szInterface)) {
                return true;
            } else {
                Class[] face1 = face[i].getInterfaces();
                for (int x = 0; x < face1.length; x++) {
                    if (face1[x].getName().equals(szInterface)) {
                        return true;
                    } else if (isInterface(face1[x], szInterface)) {
                        return true;
                    }
                }
            }
        }
        if (null != c.getSuperclass()) {
            return isInterface(c.getSuperclass(), szInterface);
        }
        return false;
    }
}
