package com.gujingli.basemvplibrary.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.gujingli.basemvplibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjl on 2017/7/21.
 */

public abstract class BaseAdapter<V> extends RecyclerView.Adapter<BaseHolder> {

    public List<V> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private int mLayoutId;


    public BaseAdapter(RecyclerView recyclerView, int mLayoutId) {
        super();
        this.recyclerView = recyclerView;
        this.mLayoutId = mLayoutId;
    }

    public void add(List<V> list) {
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
        show();
    }

    public void delete(int position) {
        list.remove(position);
        notifyDataSetChanged();
        show();
    }

    public void refresh(List<V> list) {
        this.list.clear();
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
        }
        show();
        notifyDataSetChanged();
    }

    public void refresh(V[] list) {
        this.list.clear();
        for (V v : list) {
            this.list.add(v);
        }
        show();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void show() {
        if (list.size() > 0) {
            recyclerView.setBackgroundResource(R.color.transparent);
        } else {
            recyclerView.setBackgroundResource(R.drawable.empty_bg);
        }
    }

    public V getBean(int position) {
        return list.get(position);
    }

    @Override
    public BaseHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        BaseHolder baseHolder = BaseHolder.get(parent, mLayoutId);
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        convert(holder, getBean(position), position);
    }

    public abstract void convert(BaseHolder holder, V v, int position);
}
