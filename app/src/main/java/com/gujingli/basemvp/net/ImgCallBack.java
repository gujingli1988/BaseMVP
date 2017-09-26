package com.gujingli.basemvp.net;

/**
 * Created by Administrator on 2016/12/16.
 */
public abstract class ImgCallBack{
    public abstract void success(String imgsId);
    public abstract void error(int code,String msg);
    public abstract void nonet();
}
