package com.example.a700_15isk.justtalk.adapters.holders.textholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.adapters.holders.BaseRecyclerHolder;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public class TextSendHolder extends BaseRecyclerHolder {
    TextView send_text;
    ProgressBar progressBar;
    BmobIMConversation c;
    Context context;


    public TextSendHolder(ViewGroup parent, Context context, BmobIMConversation c) {
        super(context,parent,R.layout.item_send_msg);
        progressBar=(ProgressBar) itemView.findViewById(R.id.progress);
        send_text=(TextView) itemView.findViewById(R.id.tv_message);
        this.c = c;
        this.context = context;
    }


    @Override
    public void bindData(Object o) {
        final BmobIMMessage message = (BmobIMMessage) o;
        final BmobIMUserInfo info = message.getBmobIMUserInfo();
        String content = message.getContent();
        send_text.setText(content);
        int status=message.getSendStatus();
        if (BmobIMSendStatus.SENDED.getStatus()== status){
                   progressBar.setVisibility(View.GONE);
        }
    }

}
