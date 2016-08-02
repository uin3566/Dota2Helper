package com.fangxu.dota2helper.util;

import android.content.Context;

import com.fangxu.dota2helper.R;
import com.youku.player.VideoQuality;

/**
 * Created by dear33 on 2016/8/2.
 */
public class VideoQualityUtil {

    public static int getQualityResId(VideoQuality videoQuality) {
        int res = 0;
        switch (videoQuality) {
            case STANDARD:
                res = R.string.standard;
                break;
            case HIGHT:
                res = R.string.high;
                break;
            case SUPER:
                res = R.string.super_high;
                break;
            case P1080:
                res = R.string.p1080;
                break;
        }
        return res;
    }

    public static String getQualityString(Context context, VideoQuality videoQuality) {
        int res = getQualityResId(videoQuality);
        return context.getResources().getString(res);
    }
}
