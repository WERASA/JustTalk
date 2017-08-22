package com.example.a700_15isk.justtalk.bean;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public class MsgBean {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;
    private String content;
    private int type;

    public MsgBean(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
