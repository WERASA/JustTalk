package com.example.a700_15isk.justtalk.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.LoginPagerAdapter;
import com.example.a700_15isk.justtalk.fragments.LoginFragment;
import com.example.a700_15isk.justtalk.fragments.RegisterFragment;
import com.example.a700_15isk.justtalk.tools.ActivityManager;
import com.example.a700_15isk.justtalk.tools.bmobtools.BombInitialize;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
   private ViewPager login_paper;
    private List<Fragment>mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BombInitialize.BombInit();
        ActivityManager.addActivity(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    public void init() {
        mFragments=new ArrayList<>();
        login_paper=(ViewPager)findViewById(R.id.login_pager);
        LoginFragment loginFragment=new LoginFragment();
        RegisterFragment registerFragment=new RegisterFragment();
        mFragments.add(registerFragment);
        mFragments.add(loginFragment);
        FragmentManager fragmentManager=getSupportFragmentManager();
        LoginPagerAdapter loginPagerAdapter =new LoginPagerAdapter(fragmentManager,mFragments);
        login_paper.setAdapter(loginPagerAdapter);



    }

    @Override
    public void onClick(View v) {

    }


}
