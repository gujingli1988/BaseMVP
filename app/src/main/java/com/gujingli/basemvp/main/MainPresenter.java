package com.gujingli.basemvp.main;

import android.text.TextUtils;

import com.gujingli.basemvp.bean.MovieBean;
import com.gujingli.basemvp.mypresenter.MyListPrestenter;
import com.gujingli.basemvp.net.HttpUtil;
import com.gujingli.basemvp.net.JsonCallBack;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by gjl on 2017/9/26.
 */

public class MainPresenter extends MyListPrestenter<MainView> {
    private int start = 0;
    private final String count = "10";

    @Override
    public void load(final boolean isMore) {
        mView.showLoading();
        if (isMore) {
            start += 1;
        } else {
            start = 1;
        }
        String url = "https://api.douban.com/v2/movie/top250";
        HashMap<String, String> map = new HashMap<>();
        map.put("start", start + "");
        map.put("count", count);
        HttpUtil.post(url, map, new JsonCallBack<MovieBean>() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (isMore) {
                    start -= 1;
                }
                showMessage(e.getMessage());
                mView.hideLoading();
            }

            @Override
            public void onResponse(MovieBean movieBean, int i) {
                mView.onLoaded(movieBean, isMore);
                mView.hideLoading();
            }

            @Override
            public void nonet() {
                if (isMore) {
                    start -= 1;
                }
                showMessage("请检查网络...");
                mView.hideLoading();
            }
        });
    }
}
