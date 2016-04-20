package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.VideoSetList;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface VideoDetailCallback {
    public void onGetVideoSetSuccess(VideoSetList videoSetList);
    public void onGetVideoSetFailed();
}
