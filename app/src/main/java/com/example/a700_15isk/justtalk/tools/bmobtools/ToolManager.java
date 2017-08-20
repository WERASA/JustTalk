package com.example.a700_15isk.justtalk.tools.bmobtools;

import com.example.a700_15isk.justtalk.bean.Friend;
import com.example.a700_15isk.justtalk.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 700-15isk on 2017/8/18.
 */

public class ToolManager {


    private  static ToolManager instance=new ToolManager();

    public static ToolManager getInstance() {
        return instance;
    }
    private ToolManager(){}


    public void queryFriends(final FindListener<Friend> listener){
        BmobQuery<Friend> query = new BmobQuery<>();
        User user =BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("user", user);
        query.include("friendUser");
        query.order("-updatedAt");
        query.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
           //     e.printStackTrace();
                if (e==null){
                    listener.done(list,e);
                }
                else
                    e.printStackTrace();



            }


    });



}
}
