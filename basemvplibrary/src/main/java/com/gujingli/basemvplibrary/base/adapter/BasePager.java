package com.gujingli.basemvplibrary.base.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjl on 2017/7/19.
 */

public abstract class BasePager<T> extends PagerAdapter {


    private ArrayList<T> list_picture = new ArrayList<>();

    @Override
    public int getCount() {
        return list_picture.size();
    }

    public void upDate(List<T> img_url) {
        list_picture.clear();
        list_picture.addAll(img_url);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = initDate(container, position, list_picture.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public abstract View initDate(ViewGroup container, int position, T t);
}