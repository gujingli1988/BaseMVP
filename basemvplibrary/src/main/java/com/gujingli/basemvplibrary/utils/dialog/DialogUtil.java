package com.gujingli.basemvplibrary.utils.dialog;

import android.content.Context;

public class DialogUtil {

    public static CustomProgressDialog dialog;

    public static void showProgressDialog(Context context) {
        if (dialog == null) {
            dialog = new CustomProgressDialog(context);
        }
        dialog.show();
    }

    public static void closeProgressDialog() {
        if (isProgressDialogShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static boolean isProgressDialogShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }
}
