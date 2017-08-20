package com.example.a700_15isk.justtalk.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a700_15isk.justtalk.tools.bmobtools.ToolManager;
import com.example.a700_15isk.justtalk.bean.Friend;
import com.example.a700_15isk.justtalk.databinding.FragmentFriendsBinding;
import com.example.a700_15isk.justtalk.tools.MyApp;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.FriendsRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by 700-15isk on 2017/8/17.
 */

public class FriendFragment extends Fragment implements View.OnClickListener {
  FragmentFriendsBinding mBinding;
    List<Friend> friends=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_friends,container,false);
        init();
        return mBinding.getRoot();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void init(){
        if (isAdded()){
            ToolManager.getInstance().queryFriends(new FindListener<Friend>() {
                @Override
                public void done(List<Friend> list, BmobException e) {
                    if (e==null){
                    friends=list;
                    FriendsRecycleAdapter friendsRecycleAdapter=new FriendsRecycleAdapter(list);
                    mBinding.friendlist.setAdapter(friendsRecycleAdapter);
                    mBinding.friendlist.setLayoutManager(new LinearLayoutManager(MyApp.getMyAppContext()));}
                    else
                        e.printStackTrace();
                }
            });

        }


    }


    @Override
    public void onClick(View v) {

    }
}
