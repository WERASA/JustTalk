package com.example.a700_15isk.justtalk.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.NewFriendAdapter;
import com.example.a700_15isk.justtalk.bean.Conversation;
import com.example.a700_15isk.justtalk.bean.NewFriend;
import com.example.a700_15isk.justtalk.bean.NewFriendConversation;
import com.example.a700_15isk.justtalk.databinding.FragmentNewFriendBinding;
import com.example.a700_15isk.justtalk.tools.NewFriendManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 700-15isk on 2017/8/24.
 */

public class NewFriendFragment extends Fragment {
    FragmentNewFriendBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_friend, container, false);
        init();
        return mBinding.getRoot();
    }

    public void init() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        mBinding.newFriendList.setLayoutManager(linearLayoutManager);
        List<NewFriend>newFriends=NewFriendManager.getInstance(MyApp.getMyAppContext()).getAllNewFriend();

        if (newFriends!=null&&newFriends.size()>0){
        mBinding.newFriendList.setAdapter(new NewFriendAdapter(newFriends));}
        else
            mBinding.emptyText.setText("暂无新好友");

    }


    private List<Conversation> getConversations() {
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        //添加新朋友会话-获取好友请求表中最新一条记录
        List<NewFriend> friends = NewFriendManager.getInstance(getActivity()).getAllNewFriend();
        if (friends != null && friends.size() > 0) {
            conversationList.add(new NewFriendConversation(friends.get(0)));
        }
        //重新排序

        return conversationList;
    }
}
