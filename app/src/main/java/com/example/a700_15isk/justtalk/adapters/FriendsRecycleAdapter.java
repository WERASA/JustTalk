package com.example.a700_15isk.justtalk.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.bean.Friend;
import com.example.a700_15isk.justtalk.MyApp;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 700-15isk on 2017/8/17.
 */

public class FriendsRecycleAdapter extends RecyclerView.Adapter<FriendsRecycleAdapter.FriendsHolder> implements View.OnClickListener {

    FriendsHolder friendsHolder;
    List<Friend> friends = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener = null;


    public FriendsRecycleAdapter(List<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        view.setOnClickListener(this);
        friendsHolder = new FriendsHolder(view);
        return friendsHolder;
    }

    @Override
    public void onBindViewHolder(FriendsHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.userNick.setText(friends.get(position).getFriendUser().getNick());
        if (friends.get(position).getFriendUser().getUsername() != null) {
            holder.userAccount.setText(friends.get(position).getFriendUser().getUsername());
        }

        if (friends.get(position).getFriendUser().getAvatar() != null&&!friends.get(position).getFriendUser().getAvatar().equals("")) {
            Glide.with(MyApp.getMyAppContext()).load(friends.get(position).getFriendUser().getAvatar()).into(holder.userAvatar);
        }
    }


    @Override
    public int getItemCount() {
        return friends.size();
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

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {
        TextView userNick;
        TextView userAccount;
        CircleImageView userAvatar;

        public FriendsHolder(View itemView) {
            super(itemView);
            userAvatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            userNick = (TextView) itemView.findViewById(R.id.user_name);
            userAccount = (TextView) itemView.findViewById(R.id.user_account);
        }
    }
}

