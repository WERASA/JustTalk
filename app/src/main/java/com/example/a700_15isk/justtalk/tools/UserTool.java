package com.example.a700_15isk.justtalk.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.bean.User;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.BmobUser.getCurrentUser;

/**
 * Created by 700-15isk on 2017/8/19.
 */

public class UserTool {
    private static UserTool Instance = new UserTool();
    private int CODE_NULL = 0;

    private UserTool() {
    }

    public static UserTool getInstance() {
        return Instance;
    }

    public void update(User user, final Context context) {
        BmobUser b =BmobUser.getCurrentUser(context);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d("tag","success");
            }

            @Override
            public void onFailure(int i, String s) {
                  Log.d("fail",s);
            }

        });
    }


    public void register(String username, String password, final LogInListener listener, Context context) {
        if (username.equals("")) {
            listener.done(null, new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if (password.equals("")) {
            listener.done(null, new BmobException(CODE_NULL, "请填写密码"));
            return;
        }

        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(0);
        user.setSex(false);
        user.setAvatar("");
        user.setNick(RandomName.getRandomString(5));
        user.signUp(context,new SaveListener() {
            @Override
            public void onSuccess() {
                listener.done(null, null);
            }

            @Override
            public void onFailure(int i, String s) {
                listener.done(null, new BmobException(i, s));
            }


        });
    }

    public void login(String username, String password, final LogInListener listener, final Context context) {
        if(username.equals("")){
            listener.internalDone(new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if(password.equals("")){
            listener.internalDone(new BmobException(CODE_NULL, "请填写密码"));
            return;
        }
        final User user =new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                listener.done(getCurrentUser(context), null);
            }

            @Override
            public void onFailure(int i, String s) {
                listener.done(user, new BmobException(i, s));
            }
        });
    }


}
