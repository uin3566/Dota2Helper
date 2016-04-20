package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.interactor.VideoDetailCallback;
import com.fangxu.dota2helper.interactor.VideoDetailInteractor;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoDetailPresenter implements VideoDetailCallback{
    private IVideoDetailView mCallback;
    private VideoDetailInteractor mInteractor;

    public VideoDetailPresenter(IVideoDetailView iVideoDetailView) {
        mCallback = iVideoDetailView;
        mInteractor = new VideoDetailInteractor(this);
    }

    public void queryVideoSetInformation(String date, String vid) {
        mInteractor.queryVideoSetInfo(date, vid);
    }

    public void queryYoukuVid(String date, String vid) {

    }

    @Override
    public void onGetVideoSetSuccess(VideoSetList videoSetList) {
        int isValid = videoSetList.getIsvalid();
        if (isValid == 1) {
            mCallback.setVideoSet(videoSetList);
        } else {
            final String invalid = "暂不支持该类视频";
            mCallback.onVideoInvalid(invalid);
        }
    }

    @Override
    public void onGetVideoSetFailed() {
        final String error = "获取视频信息失败";
        mCallback.onGetInfoFailed(error);
    }
}
