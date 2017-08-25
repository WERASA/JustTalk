package com.example.a700_15isk.justtalk.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.NewFriendAdapter;
import com.example.a700_15isk.justtalk.bean.Conversation;
import com.example.a700_15isk.justtalk.bean.Friend;
import com.example.a700_15isk.justtalk.bean.NewFriend;
import com.example.a700_15isk.justtalk.bean.NewFriendConversation;
import com.example.a700_15isk.justtalk.databinding.FragmentNewFriendBinding;
import com.example.a700_15isk.justtalk.tools.NewFriendManager;
import com.example.a700_15isk.justtalk.tools.UserTool;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.DeleteListener;

/**
 * Created by 700-15isk on 2017/8/24.
 */

public class NewFriendFragment extends Fragment {
    FragmentNewFriendBinding mBinding;
    NewFriendAdapter newFriendAdapter;
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
        newFriendAdapter=new NewFriendAdapter(newFriends);
        if (newFriends!=null&&newFriends.size()>0){
        mBinding.newFriendList.setAdapter(newFriendAdapter);}
        setDelete();



    }
    public void setDelete(){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<NewFriend> datas = newFriendAdapter.getNewFriends();
               NewFriendManager.getInstance(getContext()).deleteNewFriend(datas.get(position));
                newFriendAdapter.remove(datas.get(position));
            }
        });
        helper.attachToRecyclerView(mBinding.newFriendList);
    }


}
