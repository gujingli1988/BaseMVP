package com.gujingli.basemvplibrary.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.gujingli.basemvplibrary.R;
import com.gujingli.basemvplibrary.utils.UiUtil;


public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {//主要是复写dialog的构造方法设置dialog的大小和样式
        super(context, R.style.CustomProgressDialog);
        setContentView(R.layout.progress_wait);//设置dialog的view布局
        this.setCancelable(true);//设置可以取消
        this.setCanceledOnTouchOutside(false);//设置点击外部是否可以取消
        //设置dialog的 大小 位置等相关信息
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UiUtil.dip2px(120);
        params.height = UiUtil.dip2px(120);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setOnKeyListener(keylistener);
    }

    private OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
}
