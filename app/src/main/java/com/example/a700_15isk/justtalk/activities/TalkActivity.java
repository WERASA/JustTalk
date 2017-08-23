package com.example.a700_15isk.justtalk.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.TalkRoomRecycleAdapter;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.databinding.ActivityTalkBinding;
import com.example.a700_15isk.justtalk.tools.ActivityManager;
import com.example.a700_15isk.justtalk.tools.bmobtools.BombInitialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;


public class TalkActivity extends AppCompatActivity implements ObseverListener, MessageListHandler {
    BmobIMConversation c;
    TalkRoomRecycleAdapter talkRoomRecycleAdapter;
    LinearLayoutManager linearLayoutManager;
    private ActivityTalkBinding mBinding;
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);

        }

        @Override
        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
            if (e == null) {
                talkRoomRecycleAdapter.addMessage(bmobIMMessage);
                mBinding.EditText.setText("");
            } else e.printStackTrace();

        }
    };
    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_talk);
        ActivityManager.addActivity(this);
        BombInitialize.BombInit();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    public void init() {

        Intent intent = this.getIntent();
        userInfo = (User) intent.getSerializableExtra("user");
        c = BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) intent.getSerializableExtra("c"));
        linearLayoutManager = new LinearLayoutManager(this);
        talkRoomRecycleAdapter = new TalkRoomRecycleAdapter(this, c);
        mBinding.talkList.setAdapter(talkRoomRecycleAdapter);
        mBinding.talkList.setLayoutManager(linearLayoutManager);
        if (userInfo != null) {
            if (userInfo.getAvatar() != null) {
                c.setConversationIcon(userInfo.getAvatar());
            }
            if (userInfo.getNick() != null) {
                mBinding.talkRoomBar.setTitle(userInfo.getNick());
                c.setConversationTitle(userInfo.getNick());
            }

        } else {
            if (c.getConversationTitle() != null) {
                mBinding.talkRoomBar.setTitle(c.getConversationTitle());
            }
        }
        mBinding.talkRoomBar.setTitleTextColor(getResources().getColor(R.color.textColor));
        mBinding.talkRoomBar.setSubtitleTextColor(getResources().getColor(R.color.textColor));
        mBinding.talkRoomBar.setNavigationIcon(R.mipmap.ic_back);

        mBinding.talkRoomBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TalkActivity.this, HomePagerActivity.class);
                i.putExtra("tag", 1);
                startActivity(i);


            }
        });
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }


    private void sendMessage() {

        String text = mBinding.EditText.getText().toString();
        if (text.isEmpty()) {
            return;
        }
        BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(text);
        Map<String, Object> map = new HashMap<>();
        map.put("level", "1");
        msg.setExtraMap(map);
        c.sendMessage(msg, listener);


    }

    private void addMessage2Chat(MessageEvent event) {
        BmobIMMessage msg = event.getMessage();
        if (c != null && event != null && c.getConversationId().equals(event.getConversation().getConversationId())) //如果是当前会话的消息){//并且不为暂态消息
            if (talkRoomRecycleAdapter.findPosition(msg) < 0) {
                talkRoomRecycleAdapter.addMessage(msg);
                c.updateReceiveStatus(msg);
                scrollToBottom();
            } else {
                Log.d("tag", "null");
            }
    }


    private void scrollToBottom() {
        linearLayoutManager.scrollToPositionWithOffset(talkRoomRecycleAdapter.getItemCount() - 1, 0);
    }

    @Override
    public void onMessageReceive(List<MessageEvent> list) {

        for (int i = 0; i < list.size(); i++) {
            addMessage2Chat(list.get(i));
        }

    }

    @Override
    protected void onResume() {
        BmobIM.getInstance().addMessageListHandler(this);
        BmobNotificationManager.getInstance(this).cancelNotification();
        super.onResume();
    }

    @Override
    protected void onPause() {
        BmobIM.getInstance().removeMessageListHandler(this);
        super.onPause();
    }
}
