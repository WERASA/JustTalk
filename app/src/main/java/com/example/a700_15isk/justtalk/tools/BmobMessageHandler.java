package com.example.a700_15isk.justtalk.tools;

import android.content.Context;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by 700-15isk on 2017/8/19.
 */

public class BmobMessageHandler extends BmobIMMessageHandler {
    private Context context;
    public BmobMessageHandler(Context context){
        this.context=context;
    }
    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        super.onMessageReceive(messageEvent);
    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        super.onOfflineReceive(offlineMessageEvent);
    }
}
