package com.example.a700_15isk.justtalk.tools;

import com.example.a700_15isk.justtalk.bean.User;

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
    static BmobIMMessage bmobIMMessage1;

    public static BmobIMMessage queryMessage(final BmobIMConversation bmobIMConversation, BmobIMMessage bmobIMMessage) {
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), bmobIMConversation);

        messageManager.queryMessages(bmobIMMessage, 1, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                bmobIMMessage1 = list.get(list.size() - 1);


            }
        });
        return bmobIMMessage1;
    }


    }



