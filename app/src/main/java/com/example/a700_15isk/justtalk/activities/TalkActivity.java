package com.example.a700_15isk.justtalk.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.TalkRoomRecycleAdapter;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.databinding.ActivityTalkBinding;
import com.example.a700_15isk.justtalk.fragments.FriendFragment;
import com.example.a700_15isk.justtalk.tools.ActivityManager;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;


public class TalkActivity extends AppCompatActivity {
    private ActivityTalkBinding mBinding;
    private User userInfo;
    BmobIMConversation c;
    TalkRoomRecycleAdapter talkRoomRecycleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_talk);
        ActivityManager.addActivity(this);

        init();
    }
    public void  init(){
        Intent intent = this.getIntent();
        userInfo=(User)intent.getSerializableExtra("user");
        c= BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) intent.getSerializableExtra("c"));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        talkRoomRecycleAdapter = new TalkRoomRecycleAdapter(this,c);
        mBinding.talkList.setAdapter(talkRoomRecycleAdapter);
        mBinding.talkList.setLayoutManager(linearLayoutManager);
        if (userInfo.getAvatar()!=null){
            c.setConversationIcon(userInfo.getAvatar());
        }
        if (userInfo.getNick()!=null){
        mBinding.talkRoomBar.setTitle(userInfo.getNick());
            c.setConversationTitle(userInfo.getNick());}
        mBinding.talkRoomBar.setTitleTextColor(getResources().getColor(R.color.textColor));
        mBinding.talkRoomBar.setSubtitleTextColor(getResources().getColor(R.color.textColor));
        mBinding.talkRoomBar.setNavigationIcon(R.mipmap.ic_back);
        if (userInfo.getUsername()!=null){
            mBinding.talkRoomBar.setSubtitle(userInfo.getUsername());
        }
        mBinding.talkRoomBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TalkActivity.this,HomePagerActivity.class);
                i.putExtra("tag",1);
                startActivity(i);
                ActivityManager.removeActivity(TalkActivity.this);
            }
        });
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }







    private void sendMessage(){
        String text=mBinding.EditText.getText().toString();
        if(text.isEmpty()){
            return;
        }
        BmobIMTextMessage msg =new BmobIMTextMessage();
        msg.setContent(text);
        Map<String,Object> map =new HashMap<>();
        map.put("level", "1");
        msg.setExtraMap(map);
        c.sendMessage(msg, listener);
        mBinding.EditText.setText("");

    }




    public MessageSendListener listener =new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);

        }

        @Override
        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
            if (e==null){
             talkRoomRecycleAdapter.addMessage(bmobIMMessage);}
            else e.printStackTrace();

        }
    };



}
