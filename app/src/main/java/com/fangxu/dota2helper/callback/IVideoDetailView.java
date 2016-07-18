package com.fangxu.dota2helper.callback;

import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoDetailInfo;
import com.fangxu.dota2helper.bean.VideoSetList;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface IVideoDetailView {
    void setVideoList(List<VideoSetList.VideoDateVidEntity> videoList);
    void setAnthologyGridGone();
    void setYoukuVid(boolean queryVideoDetail, int index, String youkuVid);
    void onGetInfoFailed(String error);
    void onVideoInvalid(String invalid);
    void setVideoDetail(String title, String published, String watchedCount, String upCount, String downCount);
    void setRelatedVideoList(List<RelatedVideoList.RelatedVideoEntity> relatedVideoList);
    void setNoRelatedVideo();
    void hideProgressBar();
}
