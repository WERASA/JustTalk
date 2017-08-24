package com.example.a700_15isk.justtalk.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.tools.ConversationUtil;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 700-15isk on 2017/8/23.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListHolder> implements View.OnClickListener {
    private List<BmobIMConversation> bmobIMConversations;
    private OnItemClickListener mOnItemClickListener = null;

    public ChatListAdapter(List<BmobIMConversation> bmobIMConversations) {
        this.bmobIMConversations = bmobIMConversations;
    }

    public void addAll(List<BmobIMConversation> bmobIMConversations) {
        bmobIMConversations.addAll(bmobIMConversations);
        notifyDataSetChanged();
    }
 public void refresh(List<BmobIMConversation> bmobIMConversations){
     bmobIMConversations.clear();
     addAll(bmobIMConversations);
 }
    @Override
    public ChatListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chats, parent, false);
        view.setOnClickListener(this);
        ChatListHolder c = new ChatListHolder(view);
        return c;
    }

    @Override
    public void onBindViewHolder(ChatListHolder holder, int position) {
        holder.itemView.setTag(position);
        if (ConversationUtil.queryMessage(bmobIMConversations.get(position), new BmobIMMessage())!=null){
        holder.chatItem.setText(ConversationUtil.queryMessage(bmobIMConversations.get(position), new BmobIMMessage()).getContent());}
        if (bmobIMConversations.get(position).getConversationTitle() != null) {
            holder.chatTitle.setText(bmobIMConversations.get(position).getConversationTitle());
        } else
            holder.chatTitle.setText("会话" + position);
        if (bmobIMConversations.get(position).getConversationIcon() != null) {
            Glide.with(MyApp.getMyAppContext())
                    .load(bmobIMConversations
                            .get(position).getConversationIcon()).
                    into(holder.chatIcon);
        }


    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }

    }

    @Override
    public int getItemCount() {
        return bmobIMConversations.size();
    }


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ChatListHolder extends RecyclerView.ViewHolder {
        CircleImageView chatIcon;
        TextView chatTitle;
        TextView chatItem;

        public ChatListHolder(View itemView) {
            super(itemView);
            chatIcon = (CircleImageView) itemView.findViewById(R.id.avatar);
            chatTitle = (TextView) itemView.findViewById(R.id.messageTitle);
            chatItem = (TextView) itemView.findViewById(R.id.messageItem);


        }
    }
}
