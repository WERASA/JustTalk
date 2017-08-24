package com.example.a700_15isk.justtalk.bean;

import com.example.a700_15isk.justtalk.R;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMConversationType;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 700-15isk on 2017/8/23.
 */

public class MyConversation extends Conversation {
    private BmobIMConversation conversation;
    private BmobIMMessage lastMsg;

    public MyConversation(BmobIMConversation conversation) {
        this.conversation = conversation;
        cType = BmobIMConversationType.setValue(conversation.getConversationType());
        cId = conversation.getConversationId();
        if (cType == BmobIMConversationType.PRIVATE) {
            cName = conversation.getConversationTitle();
            if (cName.equals("") || cName == null) cName = cId;
        } else {
            cName = "未知会话";
        }
        List<BmobIMMessage> msgs = conversation.getMessages();
        if (msgs != null && msgs.size() > 0) {
            lastMsg = msgs.get(0);
        }
    }

    @Override
    public Object getAvatar() {
        if (cType == BmobIMConversationType.PRIVATE) {
            String avatar = conversation.getConversationIcon();
            if (avatar.equals("") || avatar == null) {
                return R.drawable.text;
            } else {
                return avatar;
            }
        } else {
            return R.drawable.text;
        }
    }

    @Override
    public String getLastMessageContent() {
        return null;
    }

    @Override
    public long getLastMessageTime() {
        if(lastMsg!=null) {
            return lastMsg.getCreateTime();
        }else{
            return 0;
        }
    }
}
