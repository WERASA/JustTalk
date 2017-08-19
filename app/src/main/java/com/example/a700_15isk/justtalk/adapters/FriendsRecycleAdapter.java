package com.example.a700_15isk.justtalk.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.bombtools.ToolManager;
import com.example.a700_15isk.justtalk.bombtools.bean.Friend;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 700-15isk on 2017/8/17.
 */

public class FriendsRecycleAdapter extends RecyclerView.Adapter<FriendsRecycleAdapter.FriendsHolder> {


    FriendsHolder chatsHolder;
    List<Friend> friends = new ArrayList<>();

    public FriendsRecycleAdapter(List<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        chatsHolder = new FriendsHolder(view);
        return chatsHolder;
    }

    @Override
    public void onBindViewHolder(FriendsHolder holder, int position) {
        holder.userNick.setText(friends.get(position).getFriendUser().getNick());
        //holder.userAccount.setText(friends.get(position).getUser().getUsername());
    }


    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {
        TextView userNick;
        TextView userAccount;

        public FriendsHolder(View itemView) {
            super(itemView);
            userNick = (TextView) itemView.findViewById(R.id.user_name);
            userAccount = (TextView) itemView.findViewById(R.id.account);
        }
    }
}

