package com.example.a700_15isk.justtalk.tools;

import android.content.Context;

import com.example.a700_15isk.justtalk.MyApp;

import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.v3.exception.BmobException;
import de.greenrobot.event.EventBus;

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
        excuteMessage(messageEvent);
    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        super.onOfflineReceive(offlineMessageEvent);
        Map<String,List<MessageEvent>> map =offlineMessageEvent.getEventMap();

        //挨个检测下离线消息所属的用户的信息是否需要更新
        for (Map.Entry<String, List<MessageEvent>> entry : map.entrySet()) {
            List<MessageEvent> list =entry.getValue();
            int size = list.size();
            for(int i=0;i<size;i++){
                excuteMessage(list.get(i));
            }
        }
    }
    private void excuteMessage(final MessageEvent event){
        //检测用户信息是否需要更新
        UserTool.getInstance().updateUserInfo(event, new UpdateCacheListener() {
            @Override
            public void done(BmobException e) {
                BmobIMMessage msg = event.getMessage();
               //SDK内部内部支持的消息类型


                        EventBus.getDefault().post(event);
                    }
                }, MyApp.getMyAppContext());
            }
        };


