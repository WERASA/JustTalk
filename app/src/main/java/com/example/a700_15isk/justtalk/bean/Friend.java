package com.example.a700_15isk.justtalk.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 700-15isk on 2017/8/18.
 */

public class Friend extends BmobObject {
    private User user;
    private User friendUser;


    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
