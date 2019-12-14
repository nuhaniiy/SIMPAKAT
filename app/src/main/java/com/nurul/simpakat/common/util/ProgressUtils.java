package com.nurul.simpakat.common.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressUtils {

    private static ProgressDialog progressBasic;

    public static void show(Context context, String title, String message, boolean isCancelable) {

        if (progressBasic != null) {
            progressBasic.dismiss();
            progressBasic = null;
        }

        progressBasic = ProgressDialog.show(
                context, title, message, true, isCancelable);

        progressBasic.show();
    }

    public static void updateProgress(String message) {
        if (progressBasic != null) {
            progressBasic.setMessage(message);
        }
    }

    public static void dismiss() {
        try {
            if (progressBasic != null) {
                progressBasic.dismiss();
                progressBasic = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
