package com.gujingli.basemvplibrary.base.presenter;

/**
 * Created by gjl on 2017/7/19.
 */

public abstract class BaseListPresenter<T> extends BasePresenter<T> {
    public abstract void load(boolean isMore);
}
