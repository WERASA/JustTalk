package com.example.a700_15isk.justtalk.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.HomePagerAdapter;
import com.example.a700_15isk.justtalk.databinding.ActivityHomePagerBinding;
import com.example.a700_15isk.justtalk.fragments.ChatsFragment;
import com.example.a700_15isk.justtalk.fragments.FriendFragment;
import com.example.a700_15isk.justtalk.fragments.NewFriendFragment;
import com.example.a700_15isk.justtalk.fragments.SelfFragment;
import com.example.a700_15isk.justtalk.tools.ActivityManager;
import com.example.a700_15isk.justtalk.tools.bmobtools.BombInitialize;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import devlight.io.library.ntb.NavigationTabBar;


public class HomePagerActivity extends AppCompatActivity {
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


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
        BmobIM.getInstance().clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init() {
        BombInitialize.connectToServe(context);
        // List<BmobIMConversation>  bmobIMConversations = BmobIM.getInstance().loadAllConversation();
        mFragments = new ArrayList<>();
        mFragments.clear();
        FriendFragment friendFragment = new FriendFragment();
        SelfFragment selfFragment = new SelfFragment();
        ChatsFragment chatsFragment = new ChatsFragment();
        NewFriendFragment newFriendFragment = new NewFriendFragment();
        mFragments.add(chatsFragment);
        mFragments.add(friendFragment);
        mFragments.add(newFriendFragment);
        mFragments.add(selfFragment);
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragments);
        mBinding.homePager.setAdapter(homePagerAdapter);
        mBinding.homePager.setCurrentItem(2);
        if (getIntent().getExtras() != null) {
            mBinding.homePager.setCurrentItem(getIntent().getIntExtra("tag", 0));
        }

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
                Model.
                Builder(getDrawable(R.drawable.ic_group_add_black_24dp), Color.parseColor("#fdbc74")).title("addFriend")
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



