package com.example.a700_15isk.justtalk.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.activities.TalkActivity;
import com.example.a700_15isk.justtalk.adapters.ChatListAdapter;
import com.example.a700_15isk.justtalk.bean.Friend;
import com.example.a700_15isk.justtalk.databinding.FragmentChatsBinding;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by 700-15isk on 2017/8/19.
 */

public class ChatsFragment extends Fragment {
    FragmentChatsBinding mBinding;
    ChatListAdapter chatListAdapter;
    List<BmobIMConversation> bmobIMConversations;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chats, container, false);
        init();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnClick();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    private void init() {
       bmobIMConversations = new ArrayList<>();
        bmobIMConversations.clear();
        bmobIMConversations = BmobIM.getInstance().loadAllConversation();
        if (bmobIMConversations != null && bmobIMConversations.size() > 0) {
            chatListAdapter=new ChatListAdapter(bmobIMConversations);
            mBinding.chatsList.setLayoutManager(new LinearLayoutManager(getContext()));
            mBinding.chatsList.setAdapter(chatListAdapter);
        }
    }
    private List<BmobIMConversation> getConversations(){
        //添加会话
        List<BmobIMConversation> conversationList = new ArrayList<>();
        conversationList.clear();
        List<BmobIMConversation> list =BmobIM.getInstance().loadAllConversation();

        //添加新朋友会话-获取好友请求表中最新一条记录
        return list;
    }
    @Subscribe
    public void onEventMainThread(MessageEvent event){
        //重新获取本地消息并刷新列表
            chatListAdapter.addAll(getConversations());
    }
  public void setOnClick(){
      chatListAdapter.setOnItemClickListener(new ChatListAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(View view, int position) {
              Intent i=new Intent(getContext(), TalkActivity.class);
              Bundle bundle=new Bundle();
              bundle.putSerializable("c",bmobIMConversations.get(position));
              i.putExtras(bundle);
              startActivity(i);
          }
      });
  }

}










