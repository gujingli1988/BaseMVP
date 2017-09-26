package com.gujingli.basemvplibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.gujingli.basemvplibrary.R;

import java.lang.reflect.Field;

/**
 *
 */
public class UiUtil {
    public static int screenWidthPx; //屏幕宽 px
    public static int screenhightPx; //屏幕高 px
    public static float density;//屏幕密度
    public static int densityDPI;//屏幕密度
    public static float screenWidthDip;//  dp单位
    public static float screenHightDip;//  dp单位
    private static Resources resources;
    public static int stats_bar_height;
    public static int activity_titleHeight;

    public static void init(Context context) {
        resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        density = dm.density;
        densityDPI = dm.densityDpi;
        screenWidthPx = dm.widthPixels;
        screenhightPx = dm.heightPixels;
        screenWidthDip = px2dip(dm.widthPixels);
        screenHightDip = px2dip(dm.heightPixels);
        stats_bar_height = (int) UiUtil.getdp(R.dimen.stats_bar_height);
        activity_titleHeight = (int) UiUtil.getdp(R.dimen.activity_titleHeight);
    }

    //加载布局文件
    public static View inflateView(Context context, int id) {
        return LayoutInflater.from(context).inflate(id, null);
    }

    public static int dip2px(float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    public static float getdp(int id) {
        return resources.getDimension(id);
    }

    public static String[] getStringArray(int id) {
        return resources.getStringArray(id);
    }

    /**
     * 获取颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return resources.getColor(id);
    }

    public static int getStatusBarHeight(Context ctx) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = ctx.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    //获取TextView被截断之后的字符串
    public static String getEllipsisedText(TextView textView) {
        try {
            String text = textView.getText().toString();
            int lines = textView.getLineCount();
            int width = textView.getWidth();
            int len = text.length();

            TextUtils.TruncateAt where = TextUtils.TruncateAt.END;
            TextPaint paint = textView.getPaint();

            StringBuffer result = new StringBuffer();

            int spos = 0, cnt, tmp, hasLines = 0;

            while (hasLines < lines - 1) {
                cnt = paint.breakText(text, spos, len, true, width, null);
                if (cnt >= len - spos) {
                    result.append(text.substring(spos));
                    break;
                }

                tmp = text.lastIndexOf('\n', spos + cnt - 1);

                if (tmp >= 0 && tmp < spos + cnt) {
                    result.append(text.substring(spos, tmp + 1));
                    spos += tmp + 1;
                } else {
                    tmp = text.lastIndexOf(' ', spos + cnt - 1);
                    if (tmp >= spos) {
                        result.append(text.substring(spos, tmp + 1));
                        spos += tmp + 1;
                    } else {
                        result.append(text.substring(spos, cnt));
                        spos += cnt;
                    }
                }

                hasLines++;
            }

            if (spos < len) {
                result.append(TextUtils.ellipsize(text.subSequence(spos, len), paint, (float) width, where));
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
