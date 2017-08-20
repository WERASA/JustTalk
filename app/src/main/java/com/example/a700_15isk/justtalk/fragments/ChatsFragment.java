package com.example.a700_15isk.justtalk.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.ChatsRecycleAdapter;
import com.example.a700_15isk.justtalk.bmobtools.bean.Friend;
import com.example.a700_15isk.justtalk.databinding.FragmentChatsBinding;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;

/**
 * Created by 700-15isk on 2017/8/19.
 */

public class ChatsFragment extends Fragment {
    FragmentChatsBinding mBinding;
    List<Friend> friends = new ArrayList<>();
    List<BmobIMConversation> bmobIMConversations = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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

    }

    private void init() {

           bmobIMConversations = BmobIM.getInstance().loadAllConversation();
        if(bmobIMConversations!=null && bmobIMConversations.size()>0){
            ChatsRecycleAdapter chatsRecycleAdapter=new ChatsRecycleAdapter(bmobIMConversations);
            mBinding.chatsList.setLayoutManager(new LinearLayoutManager(getContext()));
            mBinding.chatsList.setAdapter(chatsRecycleAdapter);
            }
        }



    }







