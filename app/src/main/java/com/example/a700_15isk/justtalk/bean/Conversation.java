package com.example.a700_15isk.justtalk.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

import cn.bmob.newim.bean.BmobIMConversationType;

/**
 * Created by 700-15isk on 2017/8/23.
 */

public abstract class Conversation implements Serializable {
    protected String cId;
    protected String cName;
    protected BmobIMConversationType cType;
    abstract public Object getAvatar();


    public String getcName() {
        return cName;
    }

    public String getcId(){
        return cId;
    }
}
