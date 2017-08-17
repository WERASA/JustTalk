package com.example.a700_15isk.justtalk.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.data.UserInfoBean;
import com.example.a700_15isk.justtalk.httptools.NetWorkType;
import com.example.a700_15isk.justtalk.httptools.UserUtils;
import com.example.a700_15isk.justtalk.views.Login_Circle;

import rx.Subscriber;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private TextView login;
    private EditText account;
    private EditText password;
    private Login_Circle loginCircle;
    private ProgressBar progressBar;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


    }

    private void init() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        loginCircle = (Login_Circle) findViewById(R.id.login_Circle);
        login = (TextView) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        loginCircle.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                account.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                loginCircle.setVisibility(View.VISIBLE);
                startLoginAnimator();
                progressBar.setVisibility(View.VISIBLE);
                startNetWork(NetWorkType.LOGIN);
                break;

        }

    }


    private void startLoginAnimator() {
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(loginCircle, "radius", loginCircle.getRadius(), loginCircle.getHeight());
        objectAnimator.setDuration(5000);
        ObjectAnimator mObjectAnimator = new ObjectAnimator().ofFloat(loginCircle, "cx", loginCircle.getCx(), loginCircle.getHeight() / 2);
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setInterpolator(new BounceInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimator).with(mObjectAnimator);

        set.setDuration(1000);
        set.start();
    }


    private void startNetWork(int type) {
        if (type == NetWorkType.LOGIN) {
            userName = account.getText().toString();

            if (userName.equals("")) {
                Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            } else
                UserUtils.getInstance().setNewUser(new Subscriber<UserInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        Log.d(TAG, "onNext: " + userInfoBean);
                        Toast.makeText(LoginActivity.this, userInfoBean.getAccid(), Toast.LENGTH_SHORT).show();
                    }
                }, userName);

        }
    }
}
