package com.gujingli.basemvplibrary.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gujingli.basemvplibrary.base.activity.BaseActivity;
import com.gujingli.basemvplibrary.base.presenter.BasePresenter;
import com.gujingli.basemvplibrary.base.view.BaseView;


/**
 * Created by gjl on 2017/7/24.
 */

public abstract class BaseFragment<V extends BaseView, T extends BasePresenter> extends Fragment {
    public T presenter;

    public BaseActivity activity;

    public BaseFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getArgumentsDate();
        }
    }

    private void bindPresenter() {
        presenter = initPresenter();
        presenter.attach((V) this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bindPresenter();
        this.activity = (BaseActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.dettach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getFragmentLayout(), container, false);
        init(inflate, savedInstanceState);
        return inflate;
    }

    public abstract void getArgumentsDate();


    public abstract int getFragmentLayout();

    public abstract T initPresenter();

    public abstract void init(View inflate, Bundle savedInstanceState);

    public void openActivity(Class clazz) {
        activity.openActivity(clazz);
    }

    public void openActivity(Class clazz, Bundle bundle) {
        activity.openActivity(clazz, bundle);
    }
}

