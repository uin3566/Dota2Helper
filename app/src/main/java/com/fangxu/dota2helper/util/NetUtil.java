package com.fangxu.dota2helper.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dear33 on 2016/8/2.
 */
public class NetUtil {
    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetWorkInfo(context);
        return info != null && info.isConnected();
    }

    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetWorkInfo(context);
        if (info != null && info.isConnected()) {
            String type = info.getTypeName();
            return type.equalsIgnoreCase("WIFI");
        }
        return false;
    }

    private static NetworkInfo getNetWorkInfo(Context context) {
        Context ctx;
        if (context instanceof Application) {
            ctx = context;
        } else {
            ctx = context.getApplicationContext();
        }
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }
}
