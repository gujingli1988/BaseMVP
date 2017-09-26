package com.gujingli.basemvp.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gujingli.basemvp.R;
import com.gujingli.basemvp.bean.MovieBean;
import com.gujingli.basemvplibrary.base.activity.BaseMvpListActivity;
import com.gujingli.basemvplibrary.base.adapter.BaseAdapter;
import com.gujingli.basemvplibrary.base.adapter.BaseHolder;
import com.gujingli.basemvplibrary.utils.image.GlideUtils;
import com.gujingli.basemvplibrary.utils.view.RecycleDeliver;

import java.util.List;

public class MainActivity extends BaseMvpListActivity<MainView, MainPresenter> implements MainView<MovieBean>, View.OnClickListener {


    private MovieAdapter movieAdapter;
    private Button button;

    //初始化点击事件等,在初始化列表之后
    @Override
    protected void initAction() {
        button.setOnClickListener(this);
    }

    //获取intent,onCreate最先调用的方法
    @Override
    protected void getIntentDate() {

    }

    //Presenter初始化
    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    //头布局,没有返回-1
    @Override
    public int headLayout() {
        return -1;
    }

    //脚布局,没有返回-1
    @Override
    public int footLayout() {
        return R.layout.layout_main_foot;
    }

    //初始化RecyclerView列表
    @Override
    public void initRecyclerView(RecyclerView recyclerView) {
        setTitle("豆瓣Top250");
        button = (Button) findViewById(R.id.button);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(recyclerView, R.layout.activity_main);
        recyclerView.setAdapter(movieAdapter);
        RecycleDeliver recycleDeliver = new RecycleDeliver(10);
        recyclerView.addItemDecoration(recycleDeliver);
    }


    //回掉接口
    @Override
    public void onLoaded(MovieBean movieBean, boolean isMore) {

        List<MovieBean.SubjectsBean> subjects = movieBean.getSubjects();
        if (isMore) {
            movieAdapter.add(subjects);
        } else {
            movieAdapter.refresh(subjects);
        }
    }

    @Override
    public void onClick(View v) {
        presenter.load(false);
    }

    //列表适配器
    class MovieAdapter extends BaseAdapter<MovieBean.SubjectsBean> {

        public MovieAdapter(RecyclerView recyclerView, int mLayoutId) {
            super(recyclerView, mLayoutId);
        }

        @Override
        public void convert(BaseHolder holder, MovieBean.SubjectsBean subjectsBean, int position) {
            ImageView image = holder.getView(R.id.image);
            TextView text_title = holder.getView(R.id.text_title);
            TextView text_dis = holder.getView(R.id.text_dis);
            TextView text_evaluate = holder.getView(R.id.text_evaluate);
            RatingBar rating = holder.getView(R.id.rating);
            GlideUtils.loadImageView(MainActivity.this, subjectsBean.getImages().getMedium(), image);
            text_title.setText(subjectsBean.getTitle() + "/" + subjectsBean.getOriginal_title());
            List<String> genres = subjectsBean.getGenres();
            StringBuffer stringBuffer = new StringBuffer();
            for (String s : genres) {
                stringBuffer.append("/").append(s);
            }
            text_dis.setText(subjectsBean.getYear() + stringBuffer.toString());
            MovieBean.SubjectsBean.RatingBean rating1 = subjectsBean.getRating();
            text_evaluate.setText("评分:  " + rating1.getAverage());
            int stars = rating1.getStars();
            float f = stars / 10.0f;
            rating.setRating(f);
        }
    }
}
