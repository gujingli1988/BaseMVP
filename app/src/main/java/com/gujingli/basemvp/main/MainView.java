package com.gujingli.basemvp.main;

import com.gujingli.basemvplibrary.base.view.BaseView;

/**
 * Created by gjl on 2017/9/26.
 */

public interface MainView<M> extends BaseView {
    public void onLoaded(M m, boolean isMore);
}
