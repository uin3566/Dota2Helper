package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.VideoList;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface IVideoView {
    void setVideoList(List<VideoList.VideoEntity> videoEntityList, boolean append);
    void setRefreshFailed(boolean loadMore);
}
