package com.fangxu.dota2helper.util;

import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.fangxu.dota2helper.MyApp;
import com.fangxu.dota2helper.R;

/**
 * Created by dear33 on 2016/8/6.
 */
public class SnackbarUtil {
    public static void showSnack(View view, int msgId) {
        Resources resources = MyApp.getContext().getResources();
        Snackbar sb = Snackbar.make(view, resources.getString(msgId), Snackbar.LENGTH_SHORT);
        ((TextView) sb.getView().findViewById(R.id.snackbar_text)).setTextColor(resources.getColor(R.color.white));
        sb.getView().setBackgroundColor(resources.getColor(R.color.color_primary));
        sb.show();
    }
}
