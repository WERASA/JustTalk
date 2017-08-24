package com.example.a700_15isk.justtalk.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

import cn.bmob.newim.bean.BmobIMConversationType;

/**
 * Created by 700-15isk on 2017/8/23.
 */

public abstract class Conversation implements Serializable,Comparable {
    protected String cId;
    protected String cName;
    protected BmobIMConversationType cType;
    abstract public Object getAvatar();

    abstract public String getLastMessageContent();
    abstract public long getLastMessageTime();

    public String getcName() {
        return cName;
    }

    public String getcId(){
        return cId;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Conversation){
            Conversation anotherConversation = (Conversation) another;
            long timeGap = anotherConversation.getLastMessageTime() - getLastMessageTime();
            if (timeGap > 0) return  1;
            else if (timeGap < 0) return -1;
            return 0;
        }else{
            throw new ClassCastException();
        }
    }
}
