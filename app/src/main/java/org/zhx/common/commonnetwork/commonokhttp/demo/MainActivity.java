package org.zhx.common.commonnetwork.commonokhttp.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.zhx.common.commonnetwork.HttpManager;
import org.zhx.common.commonnetwork.commonokhttp.OkConfig;
import org.zhx.common.commonnetwork.commonokhttp.OkConfigBuilder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkConfig config = new OkConfigBuilder()
                .build();
        textView = findViewById(R.id.result_tv);
        HttpManager.getInstance().creatClientFromCofig(config);
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

            }
        });
    }
}
