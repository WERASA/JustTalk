package com.example.a700_15isk.justtalk.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a700_15isk.justtalk.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 700-15isk on 2017/8/19.
 */

public class ChatsRecycleAdapter extends RecyclerView.Adapter<ChatsRecycleAdapter.ChatsHolder>{

    ChatsHolder chatsHolder;
    List<BmobIMConversation> imConversations = new ArrayList<>();


    public ChatsRecycleAdapter(List<BmobIMConversation> conversations) {
        this.imConversations = conversations;
    }

    @Override
    public ChatsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chats, parent, false);
        chatsHolder = new ChatsHolder(view);
        return chatsHolder;
    }

    @Override
    public void onBindViewHolder(ChatsHolder holder, int position) {
        List<BmobIMMessage>bmobIMMessages=new ArrayList<>();
        bmobIMMessages.addAll(imConversations.get(position).getMessages());
        holder.conversationTitle.setText(imConversations.get(position).getConversationTitle());
        holder.conversationTitle.setText(imConversations.get(position).getMessages().get(bmobIMMessages.size()).getContent());
    }


    @Override
    public int getItemCount() {
        return imConversations.size();
    }

    public class ChatsHolder extends RecyclerView.ViewHolder {
        TextView conversation;
        TextView conversationTitle;

        public ChatsHolder(View itemView) {
            super(itemView);
            conversation = (TextView) itemView.findViewById(R.id.messageItem);
            conversationTitle = (TextView) itemView.findViewById(R.id.messageTitle);
        }
    }
}
