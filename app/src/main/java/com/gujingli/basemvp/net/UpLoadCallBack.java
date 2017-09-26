package com.gujingli.basemvp.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/14.
 */
public abstract class UpLoadCallBack<T> extends Callback<T> {
    private Type mType = this.getSuperclassTypeParameter(this.getClass());
    private JSONObject object;
    private int code;
    private String msg;

    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        } else {
            ParameterizedType parameter = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
        }
    }

    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("全部数据", string);
        object = new JSONObject(string);
        code = object.getInt("code");
        String data = object.getString("data");
        Log.e("data", data);
        msg = object.getString("msg");
        if (200 == code) {
            return (new Gson()).fromJson(data, mType);
        } else {
            return null;
        }
    }

    public abstract void error(int code, String msg);

    public abstract void success(T t, String msg);

    @Override
    public void onError(Call call, Exception e, int i) {
        error(i, msg);
    }

    @Override
    public void onResponse(T t, int i) {
        if (200 == code) {
            success(t, msg);
        } else {
            error(code, msg);
        }

    }
}