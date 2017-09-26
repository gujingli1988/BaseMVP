package com.gujingli.basemvp.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;

import com.gujingli.basemvp.MyApp;
import com.gujingli.basemvplibrary.utils.UiUtil;
import com.gujingli.basemvplibrary.utils.image.BitmapUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2016/12/6.
 * author gujingli
 */
public class HttpUtil {
    /**
     * 带标记的post请求
     *
     * @param url
     * @param params
     * @param tag
     * @param callBack
     */
    public static void post(String url, HashMap<String, String> params, Object tag, JsonCallBack callBack) {
        if (null == tag) {
            post(url, params, callBack);
        } else {
            if (isConnNet()) {
                OkHttpUtils
                        .post()
                        .url(url)
                        .params(params)
                        .tag(tag)
                        .build()
                        .connTimeOut(30000)
                        .readTimeOut(30000)
                        .writeTimeOut(30000)
                        .execute(callBack);
            } else {
                callBack.nonet();
            }
        }
    }

    /**
     * 通过标记取消请求
     *
     * @param tag
     */
    public static void cancel(Object tag) {
        if (tag != null) {
            OkHttpUtils.getInstance().cancelTag(tag);
        }
    }

    /**
     * 不带标记post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void post(String url, HashMap<String, String> params, JsonCallBack callBack) {
        if (isConnNet()) {
            Log.e("请求地址", url);
            OkHttpUtils
                    .post()
                    .url(url)
                    .params(params)
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(30000)
                    .writeTimeOut(30000)
                    .execute(callBack);
        } else {
            callBack.nonet();
        }
    }

    /**
     * 检测网路是否连通
     *
     * @return
     */
    public static boolean isConnNet() {
        // 获得手机所有连接管理对象（包括对wi-fi等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) MyApp.uiContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获得网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();

                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) ;

                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void lodeImage(String url, final ImageView imageView, Object tag) {
        if (isConnNet()) {
            OkHttpUtils
                    .post()//
                    .url(url)//
                    .tag(tag)//
                    .build()//
                    .connTimeOut(30000)
                    .readTimeOut(30000)
                    .writeTimeOut(30000)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            MyApp.toast("加载失败");
                        }

                        @Override
                        public void onResponse(Bitmap bitmap, int i) {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
        } else {
            MyApp.toast("没有网络");
        }
    }


    /**
     * 带标记的post请求
     *
     * @param url
     * @param params
     * @param tag
     * @param callBack
     */
    public static void post(String url, HashMap<String, String> params, Object tag, DataCallBack callBack) {
        if (null == tag) {
            post(url, params, callBack);
        } else {
            if (isConnNet()) {
                Log.e("请求地址", url);
                OkHttpUtils
                        .post()
                        .url(url)
                        .params(params)
                        .tag(tag)
                        .build()
                        .connTimeOut(30000)
                        .readTimeOut(30000)
                        .writeTimeOut(30000)
                        .execute(callBack);
            } else {
                callBack.nonet();
            }
        }
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void post(String url, HashMap<String, String> params, DataCallBack callBack) {
        if (isConnNet()) {
            Log.e("请求地址", url);
            OkHttpUtils
                    .post()
                    .url(url)
                    .params(params)
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(30000)
                    .writeTimeOut(30000)
                    .execute(callBack);
        } else {
            callBack.nonet();
        }
    }

    /**
     * 上传图片
     *
     * @param imgs
     * @param tag
     * @param callBack
     */
    public static void upLoadImg(List<Bitmap> imgs, Object tag, final ImgCallBack callBack, String url) {
        if (isConnNet()) {
            int size = imgs.size();
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String base64 = BitmapUtil.bitmapToBase64(imgs.get(i));
                strings.add(base64);
            }
            String s = strings.toString();
            String substring = s.substring(1, s.length() - 1);
            String imgStr = substring.replace(" ", "");
            HashMap<String, String> params = new HashMap<>();
            params.put("imgStr", imgStr);
            OkHttpUtils
                    .post()
                    .url(url)
                    .params(params)
                    .tag(tag)
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(30000)
                    .writeTimeOut(30000)
                    .execute(new UpLoadCallBack<ImgBean>() {
                        @Override
                        public void error(int code, String msg) {
                            callBack.error(code, msg);
                        }

                        @Override
                        public void success(ImgBean o, String msg) {
                            callBack.success(o.imgId);
                        }
                    });
        } else {
            callBack.nonet();
        }
    }

    /**
     * 上传图片
     *
     * @param imgs
     * @param callBack
     */
    public static void upLoadImg(List<Bitmap> imgs, final ImgCallBack callBack, String url) {
        if (isConnNet()) {
            int size = imgs.size();
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String base64 = BitmapUtil.bitmapToBase64(imgs.get(i));
                strings.add(base64);
            }
            String s = strings.toString();
            String substring = s.substring(1, s.length() - 1);
            String imgStr = substring.replaceAll(" ", "");
            HashMap<String, String> params = new HashMap<>();
            params.put("imgStr", imgStr);
            Log.e("params", params.toString());
            OkHttpUtils
                    .post()
                    .url(url)
                    .params(params)
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(30000)
                    .writeTimeOut(30000)
                    .execute(new UpLoadCallBack<ImgBean>() {
                        @Override
                        public void error(int code, String msg) {
                            callBack.error(code, msg);
                        }

                        @Override
                        public void success(ImgBean o, String msg) {
                            callBack.success(o.imgId);
                        }
                    });
        } else {
            callBack.nonet();
        }
    }

    /**
     * 异步下载文件
     *
     * @param url
     */
    public static void download(final String url, FileCallBack callBack, Object tag) {
        if (isConnNet()) {
            OkHttpUtils
                    .post()//
                    .url(url)//
                    .tag(tag)//
                    .build()//
                    .connTimeOut(30000)
                    .readTimeOut(30000)
                    .writeTimeOut(30000)
                    .execute(callBack);
        } else {
            MyApp.toast("没有网络");
        }
    }

    /**
     * 异步下载文件
     *
     * @param url
     */
    public static void download(final String url, FileCallBack callBack) {
        if (isConnNet()) {
            OkHttpUtils
                    .post()//
                    .url(url)//
                    .build()//
                    .connTimeOut(30000)
                    .readTimeOut(30000)
                    .writeTimeOut(30000)
                    .execute(callBack);
        } else {
            MyApp.toast("没有网络");
        }
    }
}
