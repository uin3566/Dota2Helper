package com.fangxu.dota2helper.util;

/**
 * Created by lenov0 on 2016/4/27.
 */
public class NumberConversion {

    public static String bigNumber(int count) {
        String ret;
        if (count < 10000) {
            ret = String.valueOf(count);
        } else {
            int w = count / 10000;
            int k = (count % 10000) / 1000;
            ret = String.valueOf(w) + "." + String.valueOf(k) + "万";
        }
        return ret;
    }
}
