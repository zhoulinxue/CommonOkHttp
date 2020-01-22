package org.zhx.common.commonnetwork.commonokhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * pakage :com.gaea.modelnetwork.okhttp
 * auther :zx
 * creatTime: 2019/9/9
 * description :
 */
public class NetWorkUtil {
    /**
     * 检查网络状态
     *
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
