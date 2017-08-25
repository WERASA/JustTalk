package com.example.a700_15isk.justtalk.adapters.holders;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.R;

import java.text.SimpleDateFormat;


import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * 发送的文本类型
 */
public class SendImageHolder extends BaseRecyclerHolder {
ImageView sendImage;
  BmobIMConversation conversation;
  public SendImageHolder(Context context, ViewGroup parent,BmobIMConversation conversation)  {
    super(context,parent, R.layout.item_send_img_msg);
    sendImage=(ImageView)itemView.findViewById(R.id.tv_message);
    this.conversation =conversation;
  }

  @Override
  public void bindData(Object o) {
    BmobIMMessage msg = (BmobIMMessage)o;
    //用户信息的获取必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
    final BmobIMUserInfo info = msg.getBmobIMUserInfo();
    final BmobIMImageMessage message = BmobIMImageMessage.buildFromDB(true, msg);
    int status =message.getSendStatus();
    Glide.with(MyApp.getMyAppContext()).load(message.getContent()).into(sendImage);

}
}
