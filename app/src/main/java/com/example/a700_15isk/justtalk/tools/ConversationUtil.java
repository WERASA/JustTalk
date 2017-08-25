package com.example.a700_15isk.justtalk.tools;

import com.example.a700_15isk.justtalk.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by 700-15isk on 2017/8/22.
 */

public class ConversationUtil {
    static  List<BmobIMMessage>bmobIMMessage1=new ArrayList<>();

    public static List<BmobIMMessage> queryMessage(final BmobIMConversation bmobIMConversation, BmobIMMessage bmobIMMessage,int limit) {
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), bmobIMConversation);

        messageManager.queryMessages(bmobIMMessage, limit, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (list.size()>=1&&list!=null){
                bmobIMMessage1=list;}


            }
        });
        return bmobIMMessage1;
    }


    }



