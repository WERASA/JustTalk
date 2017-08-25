package com.example.a700_15isk.justtalk.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a700_15isk.justtalk.Config;
import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.bean.AgreeAddFriendMessage;
import com.example.a700_15isk.justtalk.bean.Conversation;
import com.example.a700_15isk.justtalk.bean.NewFriend;
import com.example.a700_15isk.justtalk.bean.NewFriendConversation;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.tools.NewFriendManager;
import com.example.a700_15isk.justtalk.tools.UserTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 700-15isk on 2017/8/24.
 */

public class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.NewFriendHolder> {
    List<NewFriend>newFriends=new ArrayList<>();



    public NewFriendAdapter( List<NewFriend>newFriend){
        this.newFriends=newFriend;
    }
    public void addAll(List<NewFriend>newFriends){
       this. newFriends.addAll(newFriends);
    }

    public void add(NewFriend newFriend){
        newFriends.add(newFriend);
    }

    public void remove(NewFriend newFriend){
        newFriends.remove(newFriend);
    }

    public void clear(){
        newFriends.clear();
    }

    @Override
    public NewFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_friend, parent, false);
        NewFriendHolder newFriendHolder=new NewFriendHolder(view);
        return newFriendHolder;
    }

    @Override
    public void onBindViewHolder(final NewFriendHolder holder, final int position) {
        if (newFriends.get(position).getAvatar()!=null){
            Glide.with(MyApp.getMyAppContext()).load(newFriends.get(position).getAvatar());
        }
        if (newFriends.get(position).getName()!=null){
          holder.name.setText(newFriends.get(position).getName());
        }
        if(newFriends.get(position).getMsg()!=null){
            holder.content.setText(newFriends.get(position).getMsg());
        }
        if(newFriends.get(position).getStatus()==null ||newFriends.get(position).getStatus()== Config.STATUS_VERIFY_NONE||
                newFriends.get(position).getStatus()==Config.STATUS_VERIFY_READED){
        holder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreeAdd(newFriends.get(position), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        holder.agree.setText("已添加");
                        holder.agree.setClickable(false);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        holder.agree.setClickable(true);
                        Toast.makeText(MyApp.getMyAppContext(),"添加好友失败:" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
        else{
            holder.agree.setText("已添加");
            holder.agree.setClickable(false);}

    }



    @Override
    public int getItemCount() {
        return newFriends.size();
    }



    private void agreeAdd(final NewFriend add, final SaveListener listener){
        User user =new User();
        user.setObjectId(add.getUid());
        UserTool.getInstance().agreeAddFriend(user, new SaveListener() {
            @Override
            public void onSuccess() {
                sendAgreeAddFriendMessage(add, listener);
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onFailure(i, s);
            }
        });
    }





    private void sendAgreeAddFriendMessage(final NewFriend add,final SaveListener listener){
        BmobIMUserInfo info = new BmobIMUserInfo(add.getUid(), add.getName(), add.getAvatar());
        //如果为true,则表明为暂态会话，也就是说该会话仅执行发送消息的操作，不会保存会话和消息到本地数据库中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info,true,null);
        //这个obtain方法才是真正创建一个管理消息发送的会话
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(),c);
        //而AgreeAddFriendMessage的isTransient设置为false，表明我希望在对方的会话数据库中保存该类型的消息
        AgreeAddFriendMessage msg =new AgreeAddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(MyApp.getMyAppContext(), User.class);
        msg.setContent("我通过了你的好友验证请求，我们可以开始聊天了!");//---这句话是直接存储到对方的消息表中的
        Map<String,Object> map =new HashMap<>();
        map.put("msg",currentUser.getUsername()+"同意添加你为好友");//显示在通知栏上面的内容
        map.put("uid",add.getUid());//发送者的uid-方便请求添加的发送方找到该条添加好友的请求
        map.put("time", add.getTime());//添加好友的请求时间
        msg.setExtraMap(map);
        conversation.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e){
                if (e == null) {//发送成功
                    //修改本地的好友请求记录
                    NewFriendManager.getInstance(MyApp.getMyAppContext()).updateNewFriend(add, Config.STATUS_VERIFIED);
                    listener.onSuccess();
                } else {//发送失败
                    listener.onFailure(e.getErrorCode(),e.getMessage());
                }
            }
        });
    }


    public class NewFriendHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView avatar;
        TextView content;
        Button agree;
        public NewFriendHolder(View itemView) {
            super(itemView);
            agree=(Button)itemView.findViewById(R.id.agree);
            name=(TextView) itemView.findViewById(R.id.messageTitle);
            avatar=(CircleImageView)itemView.findViewById(R.id.avatar);
            content=(TextView)itemView.findViewById(R.id.messageItem);
        }
    }

}
