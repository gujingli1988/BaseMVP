package com.gujingli.basemvplibrary.base.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gujingli.basemvplibrary.R;
import com.gujingli.basemvplibrary.base.presenter.BaseListPresenter;
import com.gujingli.basemvplibrary.base.view.BaseView;
import com.gujingli.basemvplibrary.utils.UiUtil;
import com.gujingli.basemvplibrary.utils.view.loading.AliFooter;
import com.gujingli.basemvplibrary.utils.view.loading.AliHeader;
import com.gujingli.basemvplibrary.utils.view.loading.SpringView;


public abstract class BaseMvpListActivity<V, T extends BaseListPresenter<V>> extends BaseActivity implements BaseView {

    public T presenter;
    private TextView text_view_title;
    private SpringView springView;
    private boolean canRefrush = true;
    public ImageView image_right;
    public TextView text_right;

    @Override
    protected int getLayout() {
        return R.layout.activity_list;
    }

    @Override
    protected int getTitleLayout() {
        bindPresenter();
        return R.layout.title;
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
        FrameLayout head = (FrameLayout) findViewById(R.id.frame_head);
        FrameLayout foot = (FrameLayout) findViewById(R.id.frame_foot);
        int headLayout = headLayout();
        int footLayout = footLayout();
        if (headLayout == -1) {
            head.setVisibility(View.GONE);
        } else {
            head.setVisibility(View.VISIBLE);
            head.addView(UiUtil.inflateView(this,headLayout));
        }
        if (footLayout == -1) {
            foot.setVisibility(View.GONE);
        } else {
            foot.setVisibility(View.VISIBLE);
            foot.addView(UiUtil.inflateView(this,footLayout));
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        initRecyclerView(recyclerView);
        springView = (SpringView) findViewById(R.id.spring_view);
        if (canRefrush) {
            springView.setHeader(new AliHeader(this, true));   //参数为：logo图片资源，是否显示文字
            springView.setFooter(new AliFooter(this));
            springView.setListener(new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                    presenter.load(false);
                }

                @Override
                public void onLoadmore() {
                    presenter.load(true);
                }
            });
        } else {
            springView.setEnabled(false);
        }
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

    public abstract int headLayout();

    public abstract int footLayout();

    public abstract void initRecyclerView(RecyclerView recyclerView);

    public void setTitle(String title) {
        text_view_title.setText(title);
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        if (springView != null) {
            springView.onFinishFreshAndLoad();
        }
        closeDialog();
    }

    public void setCanRefrush(boolean canRefrush) {
        this.canRefrush = canRefrush;
    }
}
