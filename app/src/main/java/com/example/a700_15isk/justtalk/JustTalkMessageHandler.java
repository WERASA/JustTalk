package com.example.a700_15isk.justtalk;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.a700_15isk.justtalk.activities.HomePagerActivity;
import com.example.a700_15isk.justtalk.tools.UpdateCacheListener;
import com.example.a700_15isk.justtalk.tools.UserTool;

import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;
import de.greenrobot.event.EventBus;

/**
 * Created by 700-15isk on 2017/8/23.
 */

public class JustTalkMessageHandler extends BmobIMMessageHandler {
    private Context context;
    public JustTalkMessageHandler(Context context) {
        this.context = context;
    }
    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
        Map<String,List<MessageEvent>> map =event.getEventMap();
        //挨个检测下离线消息所属的用户的信息是否需要更新
        for (Map.Entry<String, List<MessageEvent>> entry : map.entrySet()) {
            List<MessageEvent> list =entry.getValue();
            int size = list.size();
            for(int i=0;i<size;i++){
                excuteMessage(list.get(i));
            }
        }
    }
    @Override
    public void onMessageReceive(final MessageEvent event) {
        excuteMessage(event);
    }

    private void excuteMessage(final MessageEvent event){
        //检测用户信息是否需要更新
        UserTool.getInstance().updateUserInfo(event, new UpdateCacheListener() {
            @Override
            public void done(BmobException e) {
                BmobIMMessage msg = event.getMessage();
                //SDK内部内部支持的消息类型
                    if (BmobNotificationManager.getInstance(context).isShowNotification()) {//如果需要显示通知栏，SDK提供以下两种显示方式：
                        Intent pendingIntent = new Intent(context, HomePagerActivity.class);
                        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //1、多个用户的多条消息合并成一条通知：有XX个联系人发来了XX条消息
                        BmobNotificationManager.getInstance(context).showNotification(event, pendingIntent);
                        //2、自定义通知消息：始终只有一条通知，新消息覆盖旧消息
//                        BmobIMUserInfo info =event.getFromUserInfo();
//                        //这里可以是应用图标，也可以将聊天头像转成bitmap
//                        Bitmap largetIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//                        BmobNotificationManager.getInstance(context).showNotification(largetIcon,
//                                info.getName(),msg.getContent(),"您有一条新消息",pendingIntent);
                        Log.d("Tag","hi");
                    } else {//直接发送消息事件
                       Log.d("Tag","hi");
                        EventBus.getDefault().post(event);
                    }
                }
            },context);
        };
    }



