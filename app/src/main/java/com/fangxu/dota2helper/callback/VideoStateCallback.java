package com.fangxu.dota2helper.callback;

/**
 * Created by dear33 on 2016/7/18.
 */
public interface VideoStateCallback {
    void onProgressChanged(long currentTimeMillis);
    void onVideoStart();
}
