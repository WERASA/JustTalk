package com.example.a700_15isk.justtalk.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a700_15isk.justtalk.PopuActivity;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.HomePagerAdapter;
import com.example.a700_15isk.justtalk.databinding.ActivityHomePagerBinding;
import com.example.a700_15isk.justtalk.fragments.ChatsFragment;
import com.example.a700_15isk.justtalk.fragments.FriendFragment;
import com.example.a700_15isk.justtalk.fragments.SelfFragment;
import com.example.a700_15isk.justtalk.tools.ActivityManager;
import com.example.a700_15isk.justtalk.tools.bmobtools.BombInitialize;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;


public class HomePagerActivity extends AppCompatActivity  {
    public Context context;
    ActivityHomePagerBinding mBinding;
    private List<Fragment> mFragments;
    private ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_pager);
        ActivityManager.addActivity(this);
        context = this;
        init();
        BombInitialize.connectToServe(context);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init() {

        mFragments = new ArrayList<>();
        FriendFragment friendFragment = new FriendFragment();
        SelfFragment selfFragment = new SelfFragment();
        ChatsFragment chatsFragment = new ChatsFragment();
        mFragments.add(chatsFragment);
        mFragments.add(friendFragment);

        mFragments.add(selfFragment);
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragments);
        mBinding.homePager.setAdapter(homePagerAdapter);
        setNavigationTabBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setNavigationTabBar() {

        models.add(new NavigationTabBar.
                Model.Builder(getDrawable(R.drawable.ic_chat_black_24dp), Color.parseColor("#73d1b4"))
                .title("chats")
                .badgeTitle("NTB").build());
        models.add(new NavigationTabBar.
                Model.
                Builder(getDrawable(R.drawable.ic_people_black_24dp), Color.parseColor("#dd6295")).title("friends")
                .badgeTitle("NTB")
                .build());


        models.add(new NavigationTabBar.
                Model.Builder(getDrawable(R.drawable.ic_person_black_24dp), Color.parseColor("#76accf"))
                .title("self")
                .badgeTitle("NTB").build());
        mBinding.bottomBar.setModels(models);
        mBinding.bottomBar.setViewPager(mBinding.homePager);

    }




}



