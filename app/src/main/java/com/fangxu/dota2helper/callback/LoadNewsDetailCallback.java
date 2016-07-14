package com.fangxu.dota2helper.callback;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface LoadNewsDetailCallback {
    void onLoadedSuccess(boolean toVideoActivity, String content);
    void onLoadedFailed();
}
