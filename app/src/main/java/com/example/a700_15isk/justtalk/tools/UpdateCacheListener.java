package com.example.a700_15isk.justtalk.tools;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;

public abstract class UpdateCacheListener extends BmobListener {
    public abstract void done(BmobException e);

    @Override
    protected void postDone(Object o, BmobException e) {
        done(e);
    }
}
