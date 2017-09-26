package com.gujingli.basemvp.mypresenter;


import com.gujingli.basemvp.MyApp;
import com.gujingli.basemvplibrary.base.presenter.BasePresenter;

/**
 * Created by gjl on 2017/9/13.
 */

public abstract class MyPrestenter<T> extends BasePresenter<T> {
    @Override
    public void showMessage(String msg) {
        MyApp.toast(msg);
    }
}
