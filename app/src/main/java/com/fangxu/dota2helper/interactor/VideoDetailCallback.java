package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoDetailInfo;
import com.fangxu.dota2helper.bean.VideoSetList;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface VideoDetailCallback {
    public void onGetVideoSetSuccess(VideoSetList videoSetList);
    public void onGetVideoSetFailed();
    public void onGetVideoDetailSuccess(VideoDetailInfo videoDetailInfo);
    public void onGetVideoDetailFailed();
    public void onGetYoukuVidSuccess(int index, String vid);
    public void onGetYoukuVidFailed();
    public void onGetRelatedVideoListSuccess(List<RelatedVideoList.RelatedVideoEntity> relatedVideoEntityList);
    public void onGetRelatedVideoListFailed();
}
