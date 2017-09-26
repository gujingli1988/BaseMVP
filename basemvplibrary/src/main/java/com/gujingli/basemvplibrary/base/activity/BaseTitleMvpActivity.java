package com.gujingli.basemvplibrary.base.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gujingli.basemvplibrary.R;
import com.gujingli.basemvplibrary.base.presenter.BasePresenter;


public abstract class BaseTitleMvpActivity<V, T extends BasePresenter<V>> extends BaseActivity {

    public T presenter;
    private TextView text_view_title;
    public ImageView image_right;
    public TextView text_right;

    private void bindPresenter() {
        presenter = initPresenter();
        presenter.attach((V) this);
    }

    @Override
    protected void initView() {
        text_view_title = (TextView) findViewById(R.id.text_view_title);
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        image_right = (ImageView) findViewById(R.id.image_right);
        text_right = (TextView) findViewById(R.id.text_right);
        initDate();
    }


    public void setTitle(String title) {
        text_view_title.setText(title);
    }


    @Override
    protected int getTitleLayout() {
        bindPresenter();
        return R.layout.title;
    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    public abstract T initPresenter();

    public abstract void initDate();

}
