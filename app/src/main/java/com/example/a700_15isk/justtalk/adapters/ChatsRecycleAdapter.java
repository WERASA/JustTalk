package com.example.a700_15isk.justtalk.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.tools.ConversationUtil;
import com.example.a700_15isk.justtalk.tools.MyApp;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 700-15isk on 2017/8/19.
 */

public class ChatsRecycleAdapter extends RecyclerView.Adapter<ChatsRecycleAdapter.ChatHolder> implements View.OnClickListener {
    ChatHolder chatHolder;

    List<BmobIMConversation> imConversations = new ArrayList<>();
    OnItemClickListener mOnItemClickListener;

    public ChatsRecycleAdapter(List<BmobIMConversation> conversations) {
        this.imConversations = conversations;
    }


    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chats, parent, false);
        view.setOnClickListener(this);
        chatHolder = new ChatHolder(view);
        return chatHolder;
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
       if (imConversations.get(position).getConversationTitle()!=null){
           holder.mMessage.setText(imConversations.get(position).getConversationTitle());
       }
      if (imConversations.get(position).getConversationIcon()!=null){
        Glide.with(MyApp.getMyAppContext())
                .load(imConversations.get(position)
                .getConversationIcon())
                .into(holder.avatar);
    }
    BmobIMMessage msg;
     msg= ConversationUtil.queryMessage(imConversations.get(position),new BmobIMMessage());
    if (msg.getContent()!=null)  {
         holder.title.setText(msg.getContent());
     }

    }

    @Override
    public int getItemCount() {
        return imConversations.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }





    public class ChatHolder extends RecyclerView.ViewHolder {
        TextView mMessage;
        TextView title;
        CircleImageView avatar;
        public ChatHolder(View itemView) {
            super(itemView);
            avatar=(CircleImageView)itemView.findViewById(R.id.avatar);
            mMessage=(TextView)itemView.findViewById(R.id.messageTitle);
            title=(TextView) itemView.findViewById(R.id.messageItem);
        }
    }

}
