package com.nurul.simpakat.common.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogUtils {

    public static void showMessage(Context context, String title, String message, boolean isCancelable) {
        AlertDialog.Builder builder = null;

        try {
            builder = new AlertDialog.Builder(context);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (builder != null) {
            AlertDialog alertDialog = builder.setMessage(message)
                    .setTitle(title)
                    .setCancelable(isCancelable)
                    .setPositiveButton(context.getString(android.R.string.ok), (dialog, which) -> dialog.dismiss())
                    .create();
            alertDialog.show();
        }
    }

    public static void showMessage(Context context, String title, String message, boolean isCancelable, DialogInterface.OnDismissListener onDismissListener) {
        AlertDialog.Builder builder = null;

        try {
            builder = new AlertDialog.Builder(context);
            AlertDialog alertDialog = builder.setMessage(message)
                    .setTitle(title)
                    .setCancelable(isCancelable)
                    .setPositiveButton(context.getString(android.R.string.ok), (dialog, which) -> dialog.dismiss())
                    .setOnDismissListener(onDismissListener)
                    .create();
            alertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void showConfirmation(
            Context context,
            String title,
            String message,
            boolean isCancelable,
            DialogInterface.OnClickListener onPositive,
            DialogInterface.OnClickListener onNegative) {
        AlertDialog.Builder builder = null;

        try {
            builder = new AlertDialog.Builder(context);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (builder != null) {
            AlertDialog alertDialog = builder.setMessage(message)
                    .setTitle(title)
                    .setCancelable(isCancelable)
                    .setPositiveButton(context.getString(android.R.string.ok), onPositive)
                    .setNegativeButton(context.getString(android.R.string.cancel), onNegative)
                    .create();
            alertDialog.show();
        }
    }

}
