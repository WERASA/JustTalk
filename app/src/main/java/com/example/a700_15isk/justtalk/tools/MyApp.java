package com.example.a700_15isk.justtalk.tools;

import android.app.Application;
import android.content.Context;

import cn.bmob.newim.BmobIM;

/**
 * Created by 700-15isk on 2017/8/18.
 */

public class MyApp extends Application {
    private static Context MY_APP_CONTEXT;
    public static Context getMyAppContext() {
    return MY_APP_CONTEXT;
}public static void setMyAppContext(Context myAppContext) {
    MY_APP_CONTEXT = myAppContext;
}
    @Override
    public void onCreate() {
        super.onCreate();
        setMyAppContext(this);
        BmobIM.init(this);
        //注册消息接收器
        BmobIM.registerDefaultMessageHandler(new BmobMessageHandler(this));
    }
}
