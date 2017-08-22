package com.example.a700_15isk.justtalk.bean;

import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public class AddFriendMessage extends BmobIMMessage {
    public AddFriendMessage(){}
    @Override
    public String getMsgType() {
        return "add";
    }

    @Override
    public boolean isTransient() {

        return true;
    }

}
