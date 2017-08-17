package com.example.a700_15isk.justtalk.httptools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 700-15isk on 2017/8/12.
 */

public class NetWorkUtil {
    public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivity = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info != null && info.isConnected())
        {
            // 当前网络是连接的
            if (info.getState() == NetworkInfo.State.CONNECTED)
            {
                // 当前所连接的网络可用
                return true;
            }
        }
    }
    return false;
}
}
