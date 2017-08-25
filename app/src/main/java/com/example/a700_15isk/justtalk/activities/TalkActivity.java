package com.example.a700_15isk.justtalk.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.a700_15isk.justtalk.Config;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.TalkRoomRecycleAdapter;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.databinding.ActivityTalkBinding;
import com.example.a700_15isk.justtalk.tools.ActivityManager;
import com.example.a700_15isk.justtalk.tools.BitMapUtil;
import com.example.a700_15isk.justtalk.tools.RandomName;
import com.example.a700_15isk.justtalk.tools.bmobtools.BombInitialize;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMFileMessage;
import cn.bmob.newim.bean.BmobIMImageMessage;
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
    String savePhotoPath;
    TalkRoomRecycleAdapter talkRoomRecycleAdapter;
    LinearLayoutManager linearLayoutManager;
    private ActivityTalkBinding mBinding;
    private String path;
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);

        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
            talkRoomRecycleAdapter.addMessage(msg);
            mBinding.EditText.setText("");
            scrollToBottom();
        }

        @Override
        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
            if (e == null) {
                Log.d("path", path);
                talkRoomRecycleAdapter.notifyDataSetChanged();
                //  mBinding.EditText.setText("");
            } else e.printStackTrace();

        }
    };
    private User userInfo;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_talk);
        ActivityManager.addActivity(this);
        BombInitialize.BombInit();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        savePhotoPath= Environment.getExternalStorageDirectory().getPath();;
        savePhotoPath=savePhotoPath+ "/"+RandomName.getRandomString(2)+".png";
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
        c = BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) intent.getSerializableExtra("conversation"));
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
        setListener();

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
public void sendImageFile(String filePath){
    BmobIMFileMessage file =new BmobIMFileMessage(filePath);
   c.sendMessage(file,listener);
}
    public void sendImageMessage(String path) {
        BmobIMImageMessage image = new BmobIMImageMessage(path);
        c.sendMessage(image, listener);
    }

    private void addMessage2Chat(MessageEvent event) {
        BmobIMMessage msg = event.getMessage();
        if (c != null && event != null && c.getConversationId().equals(event.getConversation().getConversationId())) //如果是当前会话的消息){//并且不为暂态消息
            if (talkRoomRecycleAdapter.findPosition(msg) < 0) {
                talkRoomRecycleAdapter.addMessage(msg);
                talkRoomRecycleAdapter.notifyDataSetChanged();
                c.updateReceiveStatus(msg);
                scrollToBottom();
            } else {
                Log.d("tag", "null");
            }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Config.OPEN_ALBUM_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.OPEN_ALBUM_CODE) {
            if (data == null) {
                Toast.makeText(this, "Empty Image", Toast.LENGTH_SHORT).show();
            } else {
                BitMapUtil bitMapUtil = new BitMapUtil();
                Uri uri = data.getData();
                path = bitMapUtil.getPath(uri, this);
                Log.d("log", path);
                sendImageMessage(path);
            }
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



    public void setListener() {


        mBinding.talkRoomBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo != null) {
                    Intent i = new Intent(TalkActivity.this, HomePagerActivity.class);
                    i.putExtra("tag", 1);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TalkActivity.this, HomePagerActivity.class);
                    i.putExtra("tag", 0);
                    startActivity(i);
                }

            }
        });
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        mBinding.sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });


    }
}