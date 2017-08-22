package com.example.a700_15isk.justtalk.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.bean.SexBean;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.databinding.FragmentSelfBinding;
import com.example.a700_15isk.justtalk.tools.BitMapUtil;
import com.example.a700_15isk.justtalk.tools.QiNiuUploadTool;
import com.example.a700_15isk.justtalk.tools.TextUtil;
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
   private int OPEN_ALBUM_CODE=1;
    private String path;


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
        initPickView();

        return mBinding.getRoot();

    }

    private void openAlbum() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,OPEN_ALBUM_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            Toast.makeText(getContext(),"Empty Avatar",Toast.LENGTH_SHORT).show();
        }
        else {
            BitMapUtil b=new BitMapUtil();
            Uri uri = data.getData();
            path = b.getPath(uri,getActivity());
            Log.d("log", path);
            QiNiuUploadTool.upload(path,userInfo.getNick(),userInfo,getContext());
            Glide.with(getActivity()).load(path).into(mBinding.avatar);
        }

    }

    private void init() {
      userInfo =BmobUser.getCurrentUser(getContext(), User.class);

        if (userNick!=null){
            mBinding.nickName.setText(userInfo.getNick());
        }


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
        if (userInfo.getAvatar()!=null){
            Glide.with(getActivity()).load(userInfo.getAvatar()).into(mBinding.avatar);
        }
        mBinding.sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
        mBinding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                } else
                    openAlbum();
            }
        });


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
                    UserTool.getInstance().update(userInfo, getContext());

                } else if (selectSex.equals("Woman")) {
                    userInfo.setSex(false);
                    mBinding.sex.setText("Woman");
                    UserTool.getInstance().update(userInfo, getContext());
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
        TextUtil.editTextTool(mBinding.eMail, TextUtil.CHANGE_EMAIL, userInfo, getContext());
        TextUtil.editTextTool(mBinding.age, TextUtil.CHANGE_AGE, userInfo, getContext());
        TextUtil.editTextTool(mBinding.nickName, TextUtil.CHANGE_NICK, userInfo, getContext());
    }


    public void banEnter() {
        TextUtil.banEnter(mBinding.age);
        TextUtil.banEnter(mBinding.eMail);
        TextUtil.banEnter(mBinding.nickName);
    }
}
