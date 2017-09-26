package com.gujingli.basemvp.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/14.
 */
public abstract class DataCallBack<T> extends Callback<T> {
    private Type mType = this.getSuperclassTypeParameter(this.getClass());
    private JSONObject object;
    private int code;
    private String msg;
    private static final String TAG = "OkHttp";

    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        } else {
            ParameterizedType parameter = (ParameterizedType) superclass;
            Type type = parameter.getActualTypeArguments()[0];
            return $Gson$Types.canonicalize(type);
        }
    }

    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Log.e(TAG, string);
        try {
            object = new JSONObject(string);
        } catch (JSONException e) {
        }
        try {
            code = object.getInt("code");
        } catch (JSONException e) {
        }
        String data = "";
        try {
            msg = object.getString("message");
        } catch (JSONException e) {
        }

        try {
            msg = object.getString("error");
        } catch (JSONException e) {
        }

        if (200 == code) {
            try {
                data = object.getString("data");
                return new Gson().fromJson(data, mType);
            } catch (JSONException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public abstract void nonet();

    public abstract void error(int code, String msg);

    public abstract void success(T t, String msg);

    @Override
    public void onError(Call call, Exception e, int i) {
        if (200 == code) {
            success(null, msg);
        } else {
            if (TextUtils.isEmpty(msg)) {
                msg = e.getMessage();
            }
            msg = TextUtils.isEmpty(msg) ? "服务器开小差了" : msg;
            error(code, msg);
        }
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