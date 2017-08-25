package com.example.a700_15isk.justtalk.adapters.holders;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.R;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**

 */
public class ReceiveImageHolder extends BaseRecyclerHolder {
    ImageView recImage;
    CircleImageView circleImageView;


    public ReceiveImageHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_rec_img_msg);
        recImage = (ImageView) itemView.findViewById(R.id.tv_message);
        circleImageView=(CircleImageView)itemView.findViewById(R.id.avatar) ;
        this.context = context;
    }

    @Override
    public void bindData(Object o) {
        BmobIMMessage msg = (BmobIMMessage) o;
        final BmobIMUserInfo info = msg.getBmobIMUserInfo();
        if (info.getAvatar()!=null&&!info.getAvatar().equals("")){
            Glide.with(context).load(info.getAvatar()).into(circleImageView);
        }
        final BmobIMImageMessage message = BmobIMImageMessage.buildFromDB(false,msg);
        Glide.with(context).load(message.getContent()).into(recImage);


    }
}