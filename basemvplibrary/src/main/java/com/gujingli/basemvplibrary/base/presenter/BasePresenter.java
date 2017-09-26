package com.gujingli.basemvplibrary.base.presenter;

public abstract class BasePresenter<T> {

    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }

    public abstract void showMessage(String msg);
}
