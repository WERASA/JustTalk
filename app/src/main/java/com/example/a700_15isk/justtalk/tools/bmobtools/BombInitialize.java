package com.example.a700_15isk.justtalk.tools.bmobtools;

import android.content.Context;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.event.RefreshEvent;
import com.example.a700_15isk.justtalk.tools.MyApp;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import de.greenrobot.event.EventBus;

/**
 * Created by 700-15isk on 2017/8/18.
 */

public class BombInitialize {
    public static void BombInit(){
        Bmob.initialize(MyApp.getMyAppContext(), "639f40c8a16e8c7dc43263406e135e1e");
    }
    public static void connectToServe(final Context context){
        BmobIM.init(context);
        User user = BmobUser.getCurrentUser(User.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                      if (e==null){

                          EventBus.getDefault().post(new RefreshEvent());
                      }
            }
        });
          BmobIM.getInstance().getCurrentStatus();
    }

}
