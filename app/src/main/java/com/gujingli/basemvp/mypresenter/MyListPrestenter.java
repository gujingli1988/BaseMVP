package com.gujingli.basemvp.mypresenter;


import com.gujingli.basemvp.MyApp;
import com.gujingli.basemvplibrary.base.presenter.BaseListPresenter;

/**
 * Created by gjl on 2017/9/13.
 */

public abstract class MyListPrestenter<T> extends BaseListPresenter<T> {
    @Override
    public void showMessage(String msg) {
        MyApp.toast(msg);
    }
}
