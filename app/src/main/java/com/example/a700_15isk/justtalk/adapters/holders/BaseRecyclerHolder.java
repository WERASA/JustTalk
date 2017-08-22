package com.example.a700_15isk.justtalk.adapters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 700-15isk on 2017/8/21.
 */

public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {
       Context context;

    public BaseRecyclerHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        this.context=context;

    }
    public abstract void bindData(T t);
}
