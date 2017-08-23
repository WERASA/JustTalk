package com.example.a700_15isk.justtalk;

import android.app.Application;
import android.content.Context;

import com.example.a700_15isk.justtalk.JustTalkMessageHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
    private static MyApp INSTANCE;
    public static MyApp INSTANCE(){
        return INSTANCE;
    }
    private void setInstance(MyApp app) {
        setBmobIMApplication(app);
    }
    private static void setBmobIMApplication(MyApp a) {
        MyApp.INSTANCE = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //初始化
        MY_APP_CONTEXT=this;

        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new JustTalkMessageHandler(this));
        }
        //uil初始化

    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
