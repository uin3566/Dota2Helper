package com.fangxu.dota2helper.util;

import android.content.Context;
import android.widget.Toast;

import com.fangxu.dota2helper.MyApp;

/**
 * Created by lenov0 on 2016/4/13.
 */
public class ToastUtil {
    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Context context, int strResId) {
        String str = getStringRes(context, strResId);
        if (str != null) {
            Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    }

    private static String getStringRes(Context context, int strResId) {
        if (context != null) {
            return context.getApplicationContext().getResources().getString(strResId);
        }
        return null;
    }
}
