package com.gujingli.basemvplibrary.utils.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gujingli.basemvplibrary.utils.UiUtil;

/**
 * Created by gjl on 2017/5/9.
 */

public class RecycleDeliver extends RecyclerView.ItemDecoration {


    private int top;
    private int left;
    private int right;
    private int bottom;

    public RecycleDeliver(int top, int left, int right, int bottom) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    public RecycleDeliver(int padding) {
        int px = UiUtil.dip2px(padding);
        top = px;
        left = px;
        right = px;
        bottom = px;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) == 0) {
            outRect.top = top;
        }
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;

    }

}
