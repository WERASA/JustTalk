package com.example.a700_15isk.justtalk.tools;

import android.content.Context;

import com.example.a700_15isk.justtalk.bean.User;

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
        BmobUser bmobUser = BmobUser.getCurrentUser();
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }

    public void register(String username, String password, final LogInListener listener) {
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
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.done(null, null);
                } else {
                    listener.done(null, e);
                }
            }
        });
    }

    public void login(String username, String password, final LogInListener listener) {
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
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.done(getCurrentUser(), null);
                } else {
                    listener.done(user, e);
                }
            }
        });
    }


}
