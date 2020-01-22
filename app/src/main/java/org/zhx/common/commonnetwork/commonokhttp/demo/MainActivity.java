package org.zhx.common.commonnetwork.commonokhttp.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.zhx.common.commonnetwork.commonokhttp.demo.mvp.MvpActivity;
import org.zhx.common.commonnetwork.commonokhttp.demo.mvp.presenters.WeatherPresenter;
import org.zhx.common.commonnetwork.commonokhttp.demo.mvp.views.WeatherApi;

public class MainActivity extends MvpActivity<WeatherPresenter> implements WeatherApi.view {
    private TextView textView;

    @Override
    protected WeatherPresenter initPresenter() {
        return new WeatherPresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLoadIntentData(Intent intent) {
        //TODO  获取传递过来的参数

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
        mPresenter.getWeatherInfo();
    }

    @Override
    public void onWeatherInfo(WeatherInfo info) {
        textView.setText(info.toString());
    }
}
