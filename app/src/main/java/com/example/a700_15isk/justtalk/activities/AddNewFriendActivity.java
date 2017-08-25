package com.example.a700_15isk.justtalk.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.animators.MorphAddToDialog;
import com.example.a700_15isk.justtalk.animators.MorphDialogToAdd;
import com.example.a700_15isk.justtalk.bean.AddFriendMessage;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.databinding.ActivityAddNewFriendBinding;
import com.example.a700_15isk.justtalk.tools.ActivityManager;
import com.example.a700_15isk.justtalk.tools.UserTool;
import com.example.a700_15isk.justtalk.tools.bmobtools.BombInitialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddNewFriendActivity extends AppCompatActivity {
    public static final String EXTRA_MORPH_TYPE = "morph_type";
    public static final String MORPH_TYPE_BUTTON = "morph_type_button";
    ActivityAddNewFriendBinding mBinding;
    User user;
    private ViewGroup container;
    private boolean isDismissing = false;

    public static Intent getStartIntent(Context context, String type) {
        Intent intent = new Intent(context, AddNewFriendActivity.class);
        intent.putExtra(EXTRA_MORPH_TYPE, type);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_friend);
        ActivityManager.addActivity(this);
        init();
        BombInitialize.BombInit();
        setupSharedElementTransitionsButton(this, container);
        String type = getIntent().getStringExtra(EXTRA_MORPH_TYPE);
        if (type.equals(MORPH_TYPE_BUTTON)) {
            setupSharedElementTransitionsButton(this, container);
        }
        container = (ViewGroup) findViewById(R.id.container);
        mBinding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query(mBinding.targetId.getText().toString());
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }


    private void sendAddFriendMessage(BmobIMUserInfo info) {
        BombInitialize.BombInit();
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(MyApp.getMyAppContext(), User.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getUsername());
        map.put("avatar", currentUser.getAvatar());
        map.put("uid", currentUser.getObjectId());
        msg.setExtraMap(map);
        msg.setContent(mBinding.messageContent.getText().toString());
        conversation.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    Toast.makeText(MyApp.getMyAppContext(), "消息发送成功", Toast.LENGTH_SHORT).show();
                } else {//发送失败
                    Toast.makeText(MyApp.getMyAppContext(), "消息发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupSharedElementTransitionsButton(@NonNull Activity activity,
                                                    @Nullable View target) {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);
        int color = ContextCompat.getColor(activity, R.color.itemBackground);
        Interpolator easeInOut =
                AnimationUtils.loadInterpolator(activity, android.R.interpolator.fast_out_slow_in);
        MorphAddToDialog sharedEnter = new MorphAddToDialog(color);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);
        MorphDialogToAdd sharedReturn = new MorphDialogToAdd(color);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);
        if (target != null) {
            sharedEnter.addTarget(target);
            sharedReturn.addTarget(target);
        }
        activity.getWindow().setSharedElementEnterTransition(sharedEnter);
        activity.getWindow().setSharedElementReturnTransition(sharedReturn);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dismiss(View view) {
        isDismissing = true;
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

    public void query(String userName) {
        UserTool.getInstance().queryUsers(userName, 1, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list)
            {if(list!=null&&list.size()>0){
                BmobIMUserInfo info = new BmobIMUserInfo(list.get(0).getObjectId(), list.get(0).getUsername(), list.get(0).getAvatar());
                sendAddFriendMessage(info);


            }
            else {
                Toast.makeText(AddNewFriendActivity.this,"用户不存在",Toast.LENGTH_LONG).show();
            }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        dismiss(null);
    }

}
