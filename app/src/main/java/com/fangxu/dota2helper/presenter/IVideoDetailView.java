package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.VideoSetList;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface IVideoDetailView {
    void setVideoSet(VideoSetList videoSetList);
    void onGetInfoFailed(String error);
    void onVideoInvalid(String invalid);
}
