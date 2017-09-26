package com.gujingli.basemvp.net;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/6.
 */
public abstract class JsonCallBack<T> extends Callback<T> {
    private Type mType = this.getSuperclassTypeParameter(this.getClass());

    public JsonCallBack() {
    }

    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        return (new Gson()).fromJson(string, this.mType);
    }

    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if(superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        } else {
            ParameterizedType parameter = (ParameterizedType)superclass;
            return $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
        }
    }
    public abstract void nonet();
}