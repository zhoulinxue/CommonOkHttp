package org.zhx.common.commonnetwork.commonokhttp.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.zhx.common.commonnetwork.HttpManager;
import org.zhx.common.commonnetwork.OkConfig;
import org.zhx.common.commonnetwork.OkConfigBuilder;
import org.zhx.common.commonnetwork.api.CommonNetRequest;
import org.zhx.common.commonnetwork.commonokhttp.demo.bean.WeatherInfo;
import org.zhx.common.commonnetwork.customObservable.CommonCallAdapterFactory;
import org.zhx.common.commonnetwork.impl.SimpleCommonCallBackAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {
    private TextView textView;
    private List<CommonNetRequest> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.result_tv);
        HttpManager.getInstance().init(GsonConverterFactory.create());
        //okhttp 原始用法
        HttpManager.getInstance().with(WeatherApi.class).getTest().subscribeOn(Schedulers.io())
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

            }
        });
        //封装以后的 用法
//        HttpManager.getInstance().with(WeatherApi.class).getCustomTest().excute(new SimpleCommonCallBackAdapter<WeatherInfo>() {
//            @Override
//            public void onLoadComplete() {
//
//            }
//
//            @Override
//            public void onError(String responseCode, String msg) {
//
//            }
//            @Override
//            public void onResultData(WeatherInfo info) {
//                textView.setText(info.toString());
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //封装后  防止 内存溢出 问题
        for (int i = 0; i < list.size(); i++) {
            list.get(i).cancel();
        }
    }
}
