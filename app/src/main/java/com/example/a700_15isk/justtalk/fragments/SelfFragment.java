package com.example.a700_15isk.justtalk.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.bean.SexBean;
import com.example.a700_15isk.justtalk.bmobtools.bean.User;
import com.example.a700_15isk.justtalk.databinding.FragmentSelfBinding;
import com.example.a700_15isk.justtalk.tools.UserTool;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * Created by 700-15isk on 2017/8/17.
 */

public class SelfFragment extends Fragment {
    OptionsPickerView pvOptions;
    String selectSex;
    private FragmentSelfBinding mBinding;
    private User userInfo;
    private String userNick;
    private int age;
    private boolean sex;
    private String userEmail;
    private ArrayList<SexBean> sexItem = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_self, container, false);
        setPvOptionsText();
        init();
        mBinding.sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });

        return mBinding.getRoot();

    }


    private void init() {
        userInfo = BmobUser.getCurrentUser(User.class);
        userNick = userInfo.getNick();
        if (userInfo.getAge() != null) {
            mBinding.age.setText(userInfo.getAge() + "");
        }
        if (userInfo.getSex()) {
            mBinding.sex.setText("Man");
        } else
            mBinding.sex.setText("Woman");
        if (userInfo.getEmail() != null) {
            mBinding.eMail.setText(userInfo.getEmail());
        }
        if (userInfo.getNick() != null) {
            mBinding.nickName.setText(userInfo.getNick());
        }

        initPickView();
        setTextChangeListen();
        banEnter();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initPickView() {
        pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                selectSex = sexItem.get(options1).getPickerViewText();
                if (selectSex.equals("Man")) {
                    userInfo.setSex(true);
                    mBinding.sex.setText("Man");
                    UserTool.getInstance().upDate(userInfo, getContext());

                } else if (selectSex.equals("Woman")) {
                    userInfo.setSex(false);
                    mBinding.sex.setText("Woman");
                    UserTool.getInstance().upDate(userInfo, getContext());
                }

            }
        })
                .setSubmitText("confirm")
                .setCancelText("cancel").setSubCalSize(14)
                .setCancelColor(Color.parseColor("#9d8eaf"))
                .setSubmitColor(Color.parseColor("#9d8eaf"))
                .setTitleBgColor(Color.parseColor("#605071"))
                .setContentTextSize(18)
                .setBgColor(Color.parseColor("#4a3e5c"))
                .isDialog(false).build()
        ;
        pvOptions.setPicker(sexItem);


    }


    private void setPvOptionsText() {
        sexItem.add(new SexBean("Man"));
        sexItem.add(new SexBean("Woman"));
    }


    private void setTextChangeListen() {
        mBinding.age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mBinding.age.getText().toString().equals("")) {
                    age = Integer.parseInt(mBinding.age.getText().toString());
                    userInfo.setAge(age);
                    UserTool.getInstance().upDate(userInfo, getContext());
                }
            }
        });
        mBinding.nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userInfo.setNick(mBinding.nickName.getText().toString());
                UserTool.getInstance().upDate(userInfo, getContext());
            }
        });
        mBinding.eMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userInfo.setEmail(mBinding.eMail.getText().toString());
                UserTool.getInstance().upDate(userInfo, getContext());
            }
        });
    }
    public void banEnter(){
        mBinding.nickName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return(event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        mBinding.age.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return(event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        mBinding.eMail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return(event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }
}
