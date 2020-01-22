package org.zhx.common.commonnetwork.commonokhttp.demo;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.zhx.common.mvp.DialogApi;


/**
 * @author LongpingZou
 * @date 2017/11/21
 */
public class LoadingDialog extends Dialog implements DialogApi {
    private TextView tvMsg;
    private String message;

    public LoadingDialog(Context context) {
        super(context);
        initView();
    }

    public LoadingDialog(Context context, String tvMsg) {
        super(context);
        this.message = tvMsg;
        initView();
    }

    public LoadingDialog(Context context, int src) {
        super(context);
        this.message = context.getResources().getString(src);
        initView();
    }

    public LoadingDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        this.message = strMessage;
        initView();
    }

    private void initView() {
        this.setContentView(R.layout.layout_loading);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tvMsg = this.findViewById(R.id.loading_tv);
        setCanceledOnTouchOutside(false);
    }

    public void setMessage(String message) {
        this.message = message;
        tvMsg.setText(message);
    }

    @Override
    public void showLoading() {
        if (!isShowing()) {
            show();
            if (tvMsg != null) {
                tvMsg.setText(message);
                if (TextUtils.isEmpty(message)) {
                    tvMsg.setVisibility(View.GONE);
                } else {
                    tvMsg.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    @Override
    public void dismissloading() {
        if (isShowing()) {
            dismiss();
        }
    }
}
