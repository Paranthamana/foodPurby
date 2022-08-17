package com.foodpurby.util;

import android.app.AlertDialog;
import android.content.Context;

import com.foodpurby.R;

import dmax.dialog.SpotsDialog;

/**
 * Created by tech on 8/13/2015.
 */
public class CustomProgressDialog {
    private static CustomProgressDialog ourInstance = new CustomProgressDialog();
    AlertDialog mDialog;

    public static CustomProgressDialog getInstance() {
        return ourInstance;
    }

    public void show(Context mContext) {
        try {
            if (mDialog != null)
                mDialog.dismiss();
            mDialog = new SpotsDialog(mContext, R.style.Custom);
            mDialog.setMessage("");
            mDialog.setTitle("");
            mDialog.show();
            mDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dismiss() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    public void ChageMessage(Context mContext, String title, String msg) {

        if (mDialog == null) {
            mDialog = new SpotsDialog(mContext, R.style.Custom);
        }

        mDialog.setMessage("");
        mDialog.setTitle("");
    }

    public boolean isShowing() {
        if (mDialog != null && mDialog.isShowing())
            return true;
        else
            return false;
    }
}
