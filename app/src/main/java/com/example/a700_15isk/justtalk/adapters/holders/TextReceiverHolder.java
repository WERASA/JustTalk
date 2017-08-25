package com.example.a700_15isk.justtalk.adapters.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.holders.BaseRecyclerHolder;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public class TextReceiverHolder extends BaseRecyclerHolder {
    CircleImageView userAvatar;
    TextView receiveMsg;
    BmobIMConversation c;
    Context context;

    public TextReceiverHolder(ViewGroup root, Context context) {
        super(context,root,R.layout.item_rec_msg);
        this.context=context;
        userAvatar=(CircleImageView)itemView.findViewById(R.id.avatar);
        receiveMsg=(TextView)itemView.findViewById(R.id.tv_message);
    }

    @Override
    public void bindData(Object o) {
        final BmobIMMessage message = (BmobIMMessage) o;
        final BmobIMUserInfo info = message.getBmobIMUserInfo();
        String content = message.getContent();
        receiveMsg.setText(content);
        if (info.getAvatar()!=null&&!info.getAvatar().equals("")){
           Glide.with(context).load(info.getAvatar()).into(userAvatar);}

    }
}
