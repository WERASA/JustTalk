package com.example.a700_15isk.justtalk.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.bean.AddFriendMessage;
import com.example.a700_15isk.justtalk.bean.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.BmobUser.getCurrentUser;

/**
 * Created by 700-15isk on 2017/8/19.
 */

public class UserTool {
    private static UserTool Instance = new UserTool();
    private int CODE_NULL = 0;

    private UserTool() {
    }

    public static UserTool getInstance() {
        return Instance;
    }

    public void queryUsers(String username, int limit, final FindListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        //去掉当前用户
        try {
            BmobUser user = BmobUser.getCurrentUser(MyApp.getMyAppContext());
            query.addWhereNotEqualTo("username", user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        query.addWhereContains("username", username);
        query.setLimit(limit);
        query.order("-createdAt");
        query.findObjects(MyApp.getMyAppContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list != null && list.size() > 0) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(CODE_NULL, "查无此人");
                }
            }

            @Override
            public void onError(int i, String s) {
                if (s != null) {
                    Log.d("TAG", s);
                }
            }
        });
    }

    public void queryUserInfo(String objectId, final QueryUserListener listener, Context context) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list != null && list.size() > 0) {
                    listener.internalDone(list.get(0), null);
                } else {
                    listener.internalDone(new BmobException(000, "查无此人"));
                }
            }

            @Override
            public void onError(int i, String s) {

            }


        });

    }

    public void register(String username, String password, final LogInListener listener, Context context) {
        if (username.equals("")) {
            listener.done(null, new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if (password.equals("")) {
            listener.done(null, new BmobException(CODE_NULL, "请填写密码"));
            return;
        }

        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(0);
        user.setSex(false);
        user.setAvatar("");
        user.setNick(RandomName.getRandomString(5));
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                listener.done(null, null);
            }

            @Override
            public void onFailure(int i, String s) {
                listener.done(null, new BmobException(i, s));
            }


        });
    }

    public void login(String username, String password, final LogInListener listener, final Context context) {


        if (username.equals("")) {
            listener.internalDone(new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if (password.equals("")) {
            listener.internalDone(new BmobException(CODE_NULL, "请填写密码"));
            return;
        }
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                listener.done(getCurrentUser(context), null);
            }

            @Override
            public void onFailure(int i, String s) {
                listener.done(user, new BmobException(i, s));
            }
        });
    }
    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener, Context context) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String username = info.getName();
        String title = conversation.getConversationTitle();
        if (!username.equals(title)) {
            UserTool.getInstance().queryUserInfo(info.getUserId(), new QueryUserListener() {
                @Override
                public void done(User s, BmobException e) {
                    if (e == null) {
                        String name = s.getNick();
                        String avatar = s.getAvatar();
                        conversation.setConversationIcon(avatar);
                        conversation.setConversationTitle(name);
                        info.setName(name);
                        info.setAvatar(avatar);
                        BmobIM.getInstance().updateUserInfo(info);
                        BmobIM.getInstance().updateConversation(conversation);

                    } else {

                    }
                    listener.done(null);
                }
            }, context);
        } else {
            listener.internalDone(null);
        }
    }

    private void sendAddFriendMessage(Context context, String content, BmobIMUserInfo info) {

        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null);
       
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        AddFriendMessage msg = new AddFriendMessage();
        User user = BmobUser.getCurrentUser(context, User.class);
        msg.setContent(content);//给对方的一个留言信息

        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getUsername());//发送者姓名
        map.put("avatar", user.getAvatar());//发送者的头像
        map.put("uid", user.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    Toast.makeText(MyApp.getMyAppContext(), "好友请求发送成功，等待验证", Toast.LENGTH_SHORT).show();
                } else {//发送失败
                    Toast.makeText(MyApp.getMyAppContext(), "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void update(User user, final Context context) {
        BmobUser b = BmobUser.getCurrentUser(context);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d("tag", "success");
            }

            @Override
            public void onFailure(int i, String s) {
                if (s != null) {
                    Log.d("fail", s);
                }
            }

        });
    }
}
