package com.example.a700_15isk.justtalk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.activities.HomePagerActivity;
import com.example.a700_15isk.justtalk.bean.AddFriendMessage;
import com.example.a700_15isk.justtalk.bean.AgreeAddFriendMessage;
import com.example.a700_15isk.justtalk.bean.NewFriend;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.event.RefreshEvent;
import com.example.a700_15isk.justtalk.tools.NewFriendManager;
import com.example.a700_15isk.justtalk.tools.UpdateCacheListener;
import com.example.a700_15isk.justtalk.tools.UserTool;

import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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
        UserTool.getInstance().updateUserInfo(event, new UpdateCacheListener() {
            @Override
            public void done(BmobException e) {
                BmobIMMessage msg = event.getMessage();
                if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {//用户自定义的消息类型，其类型值均为0
                    processCustomMessage(msg, event.getFromUserInfo());
                }  else {//直接发送消息事件
                        Log.d("tag","当前处于应用内，发送event");
                        EventBus.getDefault().post(event);
                    }
                }
            },context);
        }





    private void processCustomMessage(BmobIMMessage msg, BmobIMUserInfo info) {
        //消息类型
        String type = msg.getMsgType();
        AddFriendMessage addFriendMessage=new AddFriendMessage();
       AddFriendMessage agreeAddFriendMessage=new AddFriendMessage();
        //发送页面刷新的广播
        EventBus.getDefault().post(new RefreshEvent());
        //处理消息
        if(type.equals("add")){//接收到的添加好友的请求
            NewFriend friend = AddFriendMessage.convert(msg);
            //本地好友请求表做下校验，本地没有的才允许显示通知栏--有可能离线消息会有些重复
            long id = NewFriendManager.getInstance(context).insertOrUpdateNewFriend(friend);
            if(id>0){
                showAddNotify(friend);
            }
        }else if(type.equals("agree")){//接收到的对方同意添加自己为好友,此时需要做的事情：1、添加对方为好友，2、显示通知
            AgreeAddFriendMessage agree = AgreeAddFriendMessage.convert(msg);
            addFriend(agree.getFromId());//添加消息的发送方为好友
            //这里应该也需要做下校验--来检测下是否已经同意过该好友请求，我这里省略了
            showAgreeNotify(info,agree);
        }else{
            Toast.makeText(context,"接收到的自定义消息："+msg.getMsgType() + "," + msg.getContent() + "," + msg.getExtra(),Toast.LENGTH_SHORT).show();
        }
    }

    private void addFriend(String uid){
        User user =new User();
        user.setObjectId(uid);
        UserTool.getInstance().agreeAddFriend(user, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("bmob", "onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("bmob", "onFailure:"+s+"-"+i);
            }
        });
    }
    private void showAddNotify(NewFriend friend){
        Intent pendingIntent = new Intent(context, HomePagerActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //这里可以是应用图标，也可以将聊天头像转成bitmap
        Bitmap largetIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        BmobNotificationManager.getInstance(context).showNotification(largetIcon,
                friend.getName(), friend.getMsg(), friend.getName() + "请求添加你为朋友", pendingIntent);
    }
    private void showAgreeNotify(BmobIMUserInfo info,AgreeAddFriendMessage agree){
        Intent pendingIntent = new Intent(context, HomePagerActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bitmap largetIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        BmobNotificationManager.getInstance(context).showNotification(largetIcon,info.getName(),agree.getMsg(),agree.getMsg(),pendingIntent);
    }

}



