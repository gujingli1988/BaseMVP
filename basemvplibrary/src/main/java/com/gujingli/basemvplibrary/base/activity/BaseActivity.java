package com.gujingli.basemvplibrary.base.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gujingli.basemvplibrary.R;
import com.gujingli.basemvplibrary.utils.UiUtil;
import com.gujingli.basemvplibrary.utils.dialog.DialogUtil;


/**
 * activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static int width;//屏幕宽度
    public static int height;//屏幕高度
    public static final String EXITACTION = "action.exit";
    private ExitReceiver exitReceiver = new ExitReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        addExitReceiver();
        getIntentDate();
        initContent();
        initView();
        initAction();
        /**
         * 获取屏幕宽度高度
         */
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    private void addExitReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXITACTION);
        registerReceiver(exitReceiver, filter);
    }

    private void initContent() {
        FrameLayout frame_base = (FrameLayout) findViewById(R.id.frame_base);
        int layout = getLayout();
        View view = null;
        if (layout != -1) {
            view = LayoutInflater.from(this).inflate(layout, null);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
        }

        int titleLayout = getTitleLayout();
        if (titleLayout == -1) {
            if (view != null) {
                frame_base.addView(view);
            }
        } else {
            if (view != null) {
                view.setPadding(0, UiUtil.activity_titleHeight, 0, 0);
                frame_base.addView(view);
            }
            View title = LayoutInflater.from(this).inflate(titleLayout, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, UiUtil.activity_titleHeight);
            title.setLayoutParams(params);
            frame_base.addView(title);
        }
    }

    /**
     * 加载布局文件
     */
    protected abstract int getLayout();

    protected abstract int getTitleLayout();

    protected abstract void initView();

    protected abstract void initAction();

    protected abstract void getIntentDate();


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDialog();
        unregisterReceiver(exitReceiver);
    }


    public void showDialog() {
        DialogUtil.showProgressDialog(this);
    }

    public void closeDialog() {
        DialogUtil.closeProgressDialog();
    }

    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 退出应用程序的方法，发送退出程序的广播
     */
    public void exitApp() {
        Intent intent = new Intent(EXITACTION);
        this.sendBroadcast(intent);
    }

    /**
     * 实现点击空白处，软键盘消失事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            boolean shouldHideInput = isShouldHideInput(v, ev);
            if (shouldHideInput) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), /*right = left + v.getWidth();*/ right = UiUtil.screenWidthPx;
//            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
            //使用event.getRawX相对屏幕位置;event.getX()是相对控件位置
            return !(event.getRawX() > left && event.getRawX() < right && event.getRawY() > top && event.getRawY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void setBooleanSharedPreferences(String filename, String key, boolean value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected boolean getBooleanSharePreferences(String filename, String key) {
        boolean flag = this.getSharedPreferences(filename, 0).getBoolean(key, false);
        return flag;
    }

    public void setStringSharedPreferences(String filename, String key, String value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String getStringSharePreferences(String filename, String key) {
        String result = this.getSharedPreferences(filename, 0).getString(key, "");
        return result;
    }

    protected void setLongSharedpreferences(String filename, String key, long value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    protected void setIntSharedpreferences(String filename, String key, int value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    protected long getLongSharePreferences(String filename, String key) {
        long result = this.getSharedPreferences(filename, 0).getLong(key, 0);
        return result;
    }

    protected int getIntSharePreferences(String filename, String key) {
        int result = this.getSharedPreferences(filename, 0).getInt(key, 0);
        return result;
    }

    /**
     * 自适应图片大小
     */
    public static void LinearLayoutParams(ImageView imageview, double widths, double height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageview.getLayoutParams();
        params.width = (int) (width * widths);
        params.height = (int) (width * height);
        imageview.setLayoutParams(params);
    }

    public static void LinearLayoutParams1(ImageView imageview, double widths, double height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageview.getLayoutParams();
        params.width = (int) (width * widths);
        params.height = (int) (width * widths);
        imageview.setLayoutParams(params);
    }

    public static void LinearLayoutParams3(ImageView imageview, double widths, double height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageview.getLayoutParams();
        params.width = (int) (width * widths);
        params.height = (int) (width * height);
        imageview.setLayoutParams(params);
    }

    public static void RelativeLayoutParams(ImageView imageview, double widths, double height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageview.getLayoutParams();
        params.width = (int) (width * widths);
        params.height = (int) (width * height);
        imageview.setLayoutParams(params);
    }

    public static void RelativeLayoutParams(ImageView imageview, double widths) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageview.getLayoutParams();
        params.width = (int) (width * widths);
        params.height = (int) (width * widths);
        imageview.setLayoutParams(params);
    }

    public static void RelativeLayoutParams(TextView textView, double widths) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        params.width = (int) (width * widths);
        params.height = (int) (width * widths);
        textView.setLayoutParams(params);
    }

    /**
     * 自适应线性布局高度宽度
     */
    public static void LinearLayoutParams(LinearLayout relativeLayout, double widths) {
        //设置分类线性布局高度
        ViewGroup.LayoutParams lp = relativeLayout.getLayoutParams();
        lp.width = (int) (width * widths);
        lp.height = (int) (width * widths);
        relativeLayout.setLayoutParams(lp);
    }

    public static void LinearLayoutParams1(LinearLayout relativeLayout, double widths, double heights) {
        //设置分类线性布局高度
        ViewGroup.LayoutParams lp = relativeLayout.getLayoutParams();
        lp.width = (int) (width * widths);
        lp.height = (int) (height * widths);
        relativeLayout.setLayoutParams(lp);
    }

    public static void LinearLayoutParams(LinearLayout relativeLayout, double widths, double heights) {
        //设置分类线性布局高度
        ViewGroup.LayoutParams lp = relativeLayout.getLayoutParams();
        lp.width = (int) (width * widths);
        lp.height = (int) (height * heights);
        relativeLayout.setLayoutParams(lp);
    }

    public static void LinearLayoutParams2(LinearLayout linearLayout, double widths, double height) {
        //设置分类线性布局高度
        ViewGroup.LayoutParams lp = linearLayout.getLayoutParams();
        lp.width = (int) (width * widths);
        lp.height = (int) (width * height);
        linearLayout.setLayoutParams(lp);
    }

    public static void RelativeLayoutParams(RelativeLayout relativeLayout, double widths, double height) {
        //设置分类线性布局高度
        ViewGroup.LayoutParams lp = relativeLayout.getLayoutParams();
        lp.width = (int) (width * widths);
        lp.height = (int) (width * height);
        relativeLayout.setLayoutParams(lp);
    }

    public static void RelativeLayoutParamsTwo(RelativeLayout relativeLayout, double widths, double height) {
        //设置分类线性布局高度
        ViewGroup.LayoutParams lp = relativeLayout.getLayoutParams();
        lp.width = (int) (width * widths);
        lp.height = (int) (width * widths);
        relativeLayout.setLayoutParams(lp);
    }

    public static void RelativeLayoutParamsTwo2(RelativeLayout relativeLayout, double widths, double height) {
        //设置分类线性布局高度
        ViewGroup.LayoutParams lp = relativeLayout.getLayoutParams();
        lp.width = (int) (width * widths);
        lp.height = (int) (width * height);
        relativeLayout.setLayoutParams(lp);
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            BaseActivity.this.finish();
        }

    }
}
