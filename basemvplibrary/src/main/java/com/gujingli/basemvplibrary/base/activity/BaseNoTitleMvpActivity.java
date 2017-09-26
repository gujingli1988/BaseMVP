package com.gujingli.basemvplibrary.base.activity;


import com.gujingli.basemvplibrary.base.presenter.BasePresenter;

/**
 * Created by gjl on 2017/7/19.
 */

public abstract class BaseNoTitleMvpActivity<V, T extends BasePresenter<V>> extends BaseActivity {
    public T presenter;

    @Override
    protected int getTitleLayout() {
        bindPresenter();
        return initTitle();
    }

    private void bindPresenter() {
        presenter = initPresenter();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    public abstract T initPresenter();

    public abstract int initTitle();
}
