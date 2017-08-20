package com.example.a700_15isk.justtalk.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.tools.MyApp;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.activities.HomePagerActivity;
import com.example.a700_15isk.justtalk.tools.TextUtil;
import com.example.a700_15isk.justtalk.tools.UserTool;
import com.example.a700_15isk.justtalk.views.Login_Circle;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by 700-15isk on 2017/8/17.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    public float cx, radius, height;
    private TextView Register;
    private EditText account;
    private EditText password;
    private Login_Circle loginCircle;
    private ProgressBar progressBar;
    private String userName;
    private String mPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }


    private void init() {
        account = (EditText) getView().findViewById(R.id.account);
        password = (EditText) getView().findViewById(R.id.password);
        loginCircle = (Login_Circle) getView().findViewById(R.id.login_Circle);
        Register = (TextView) getView().findViewById(R.id.login);
        progressBar = (ProgressBar) getView().findViewById(R.id.login_progress);
        loginCircle.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Register.setOnClickListener(this);
        TextUtil.banEnter(password);
        TextUtil.banEnter(account);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                userLogin();
                break;

        }
    }




    public void userLogin() {
        User loginUser = new User();
        userName = account.getText().toString();
        mPassword = password.getText().toString();
        UserTool.getInstance().login(userName,mPassword, new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    startLoginAnimator();
                } else {
                    Toast.makeText(MyApp.getMyAppContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void startLoginAnimator() {
         progressBar.setVisibility(View.VISIBLE);
        password.setVisibility(View.INVISIBLE);
        account.setVisibility(View.INVISIBLE);
        loginCircle.setVisibility(View.VISIBLE);
        cx = loginCircle.getCx();
        radius = loginCircle.getRadius();
        height = loginCircle.getHeight() / 2;
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(loginCircle, "radius", loginCircle.getRadius(), loginCircle.getHeight());
        objectAnimator.setDuration(5000);
        ObjectAnimator mObjectAnimator = new ObjectAnimator().ofFloat(loginCircle, "cx", loginCircle.getCx(), loginCircle.getHeight() / 2);
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setInterpolator(new BounceInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimator).with(mObjectAnimator);
        set.setDuration(1000);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isAdded()){
                    Intent intent=new Intent(MyApp.getMyAppContext(), HomePagerActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }



}

