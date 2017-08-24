package com.example.a700_15isk.justtalk.bean;

import android.util.Log;

import com.example.a700_15isk.justtalk.MyApp;

import org.json.JSONObject;

import cn.bmob.newim.bean.BmobIMExtraMessage;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public class AddFriendMessage extends BmobIMExtraMessage {
    public static final int STATUS_VERIFY_NONE=0;
    public static NewFriend convert(BmobIMMessage msg){
        NewFriend add =new NewFriend();
        String content = msg.getContent();
        add.setMsg(content);
        add.setTime(msg.getCreateTime());
        add.setStatus(STATUS_VERIFY_NONE);
        try {
            String extra = msg.getExtra();
            if(!extra.equals("")){
                JSONObject json =new JSONObject(extra);
                String name = json.getString("name");
                add.setName(name);
                String avatar = json.getString("avatar");
                add.setAvatar(avatar);
                add.setUid(json.getString("uid"));
            }else{
                Log.d("tag：","AddFriendMessage的extra为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return add;
    }
    @Override
    public String getMsgType() {
        return "add";
    }

    @Override
    public boolean isTransient() {
        return true;
    }

}
