package com.gujingli.basemvplibrary.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    public BaseHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static BaseHolder get(ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        BaseHolder holder = new BaseHolder(itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}