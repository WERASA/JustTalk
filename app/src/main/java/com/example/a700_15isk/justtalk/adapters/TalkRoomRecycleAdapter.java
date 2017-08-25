package com.example.a700_15isk.justtalk.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.holders.BaseRecyclerHolder;

import com.example.a700_15isk.justtalk.adapters.holders.ReceiveImageHolder;
import com.example.a700_15isk.justtalk.adapters.holders.SendImageHolder;
import com.example.a700_15isk.justtalk.adapters.holders.TextReceiverHolder;
import com.example.a700_15isk.justtalk.adapters.holders.TextSendHolder;
import com.example.a700_15isk.justtalk.bean.MsgBean;
import com.example.a700_15isk.justtalk.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public class TalkRoomRecycleAdapter extends RecyclerView.Adapter {
    List<BmobIMMessage> msgs=new ArrayList<>();
    private final int TYPE_RECEIVER_TXT = 0;
    private final int TYPE_SEND_TXT = 1;
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;
    BmobIMConversation c;
    private String currentUid="";
    public TalkRoomRecycleAdapter(Context context, BmobIMConversation c) {
        try {
            currentUid = BmobUser.getCurrentUser(context).getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.c =c;

    }
    public int findPosition(long id) {
        int index = this.getCount();
        int position = -1;
        while(index-- > 0) {
            if(this.getItemId(index) == id) {
                position = index;
                break;
            }
        }
        return position;
    }

    public int findPosition(BmobIMMessage message) {
        int index = this.getCount();
        int position = -1;
        while(index-- > 0) {
            if(message.equals(this.getItem(index))) {
                position = index;
                break;
            }
        }
        return position;
    }
    public BmobIMMessage getItem(int position){
        return this.msgs == null?null:(position >= this.msgs.size()?null:this.msgs.get(position));
    }
    public int getCount() {
        return this.msgs == null?0:this.msgs.size();
    }
    public void addMessage(BmobIMMessage bmobIMMessage){
        msgs.add(bmobIMMessage);

    }

    public void addMessages(List<BmobIMMessage> bmobIMMessage){
        msgs.addAll(bmobIMMessage);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        BmobIMMessage message=msgs.get(position);
        if(message.getMsgType().equals(BmobIMMessageType.IMAGE.getType())){
            return message.getFromId().equals(currentUid) ? TYPE_SEND_IMAGE: TYPE_RECEIVER_IMAGE;
        }else if(message.getMsgType().equals(BmobIMMessageType.TEXT.getType())){
            return message.getFromId().equals(currentUid) ? TYPE_SEND_TXT: TYPE_RECEIVER_TXT;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECEIVER_TXT) {
            return new TextReceiverHolder(parent,parent.getContext());
        }else if (viewType==TYPE_SEND_TXT){
            return new TextSendHolder(parent,parent.getContext(),c);
        } else if (viewType == TYPE_RECEIVER_IMAGE) {
            return new ReceiveImageHolder(parent.getContext(), parent);
        }else if (viewType == TYPE_SEND_IMAGE) {
            return new SendImageHolder(parent.getContext(), parent,c);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseRecyclerHolder<BmobIMMessage>)holder).bindData(msgs.get(position));
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

}