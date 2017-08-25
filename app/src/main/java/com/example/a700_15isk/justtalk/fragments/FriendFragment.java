package com.example.a700_15isk.justtalk.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.activities.AddNewFriendActivity;
import com.example.a700_15isk.justtalk.activities.TalkActivity;
import com.example.a700_15isk.justtalk.adapters.FriendsRecycleAdapter;
import com.example.a700_15isk.justtalk.bean.Friend;
import com.example.a700_15isk.justtalk.bean.User;
import com.example.a700_15isk.justtalk.databinding.FragmentFriendsBinding;
import com.example.a700_15isk.justtalk.MyApp;
import com.example.a700_15isk.justtalk.tools.UserTool;
import com.example.a700_15isk.justtalk.tools.bmobtools.ToolManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by 700-15isk on 2017/8/17.
 */

public class FriendFragment extends Fragment implements View.OnClickListener {
    FragmentFriendsBinding mBinding;
    List<Friend> friends = new ArrayList<>();
    FriendsRecycleAdapter friendsRecycleAdapter;
    BmobIMUserInfo info;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        ToolManager.getInstance().queryFriends(new FindListener<Friend>() {
            @Override
            public void onSuccess(List list) {
                if (list != null && list.size() > 0) {
                    {
                        friends = list;
                        friendsRecycleAdapter = new FriendsRecycleAdapter(list);
                        mBinding.friendlist.setAdapter(friendsRecycleAdapter);
                        mBinding.friendlist.setLayoutManager(new LinearLayoutManager(MyApp.getMyAppContext()));
                        setOnItemClick();
                    }
                }
                else
                    mBinding.emptyText.setText("暂无联系人");
            }
            @Override
            public void onError(int i, String s) {}
        }, getContext());
        mBinding.fabMorph.setOnClickListener(this);
        setDelete();

    }

    public void setOnItemClick() {
        friendsRecycleAdapter.setOnItemClickListener(new FriendsRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TalkActivity.class);
                Bundle bundle = new Bundle();
                User user = friends.get(position).getFriendUser();
                info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
                bundle.putSerializable("user", friends.get(position).getFriendUser());
                intent.putExtras(bundle);
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("conversation", conversationEntrance);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_morph:
                User user= BmobUser.getCurrentUser(getContext(),User.class);
                Intent login = AddNewFriendActivity.getStartIntent(getActivity(), AddNewFriendActivity.MORPH_TYPE_BUTTON);
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                login.putExtras(bundle);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                        (getActivity(), v, getString(R.string.transition_morph_view));
                startActivity(login, options.toBundle());
        }
    }

    public void setDelete(){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<Friend> datas = friendsRecycleAdapter.getFriends();
                UserTool.getInstance().deleteFriend(datas.get(position), new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(),"Delete Success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
                friendsRecycleAdapter.remove(datas.get(position));
            }
        });
        helper.attachToRecyclerView(mBinding.friendlist);
    }

}
