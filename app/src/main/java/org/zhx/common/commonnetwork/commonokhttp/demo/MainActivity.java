package org.zhx.common.commonnetwork.commonokhttp.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.zhx.common.commonnetwork.HttpManager;
import org.zhx.common.commonnetwork.commonokhttp.OkConfig;
import org.zhx.common.commonnetwork.commonokhttp.OkConfigBuilder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private TextView textView;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLoadIntentData(Intent intent) {
        //TODO  获取传递过来的参数
        OkConfig config = new OkConfigBuilder()
                .build();
        HttpManager.getInstance().init(config);
    }

    @Override
    protected void onLoadDataFromSavedInstanceState(Bundle savedInstanceState) {
        //TODO  从低内存 获取 参数
    }

    @Override
    protected void onCreatView() {
        //TODO  初始化 组件
        textView = findViewById(R.id.result_tv);
    }

    @Override
    protected void onLoadDataFormNetWork() {
        //TODO  调用接口
        showLoadingDialog(R.string.loading_default_text);
        HttpManager.getInstance().with(weatherApi.class).getTest().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeatherInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WeatherInfo info) {
                textView.setText(info.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                dismissLoadingDialog();
            }
        });
    }
}
