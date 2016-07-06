package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.VideoList;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface VideoCallback {
    void onUpdateSuccessed(List<VideoList.VideoEntity> videoEntityList, boolean loadmore);
    void onUpdateFailed(boolean loadmore);
    void onGetCachedVideo(List<VideoList.VideoEntity> videoEntityList);
    void onCacheEmpty();
}
