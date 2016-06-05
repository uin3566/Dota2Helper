package com.fangxu.dota2helper.presenter;

import android.app.Activity;

import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoDetailInfo;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.interactor.VideoDetailCallback;
import com.fangxu.dota2helper.interactor.VideoDetailInteractor;
import com.fangxu.dota2helper.util.NumberConversion;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoDetailPresenter implements VideoDetailCallback {
    private IVideoDetailView mCallback;
    private VideoDetailInteractor mInteractor;

    public VideoDetailPresenter(Activity activity, IVideoDetailView iVideoDetailView) {
        mCallback = iVideoDetailView;
        mInteractor = new VideoDetailInteractor(activity, this);
    }

    public void queryVideoSetInformation(String date, String vid) {
        mInteractor.queryVideoSetInfo(date, vid);
    }

    public void queryYoukuVid(int index, String date, String vid) {
        mInteractor.queryYoukuVid(index, date, vid);
    }

    public void queryRelatedYoukuVideo(String vid) {
        mInteractor.queryRelatedVideoList(vid);
    }

    public void queryDetailAndRelated(String vid) {
        mInteractor.queryDetailAndRelated(vid);
    }

    @Override
    public void onGetVideoSetSuccess(VideoSetList videoSetList) {
        int videoCount = videoSetList.getVideoSet().size();
        if (videoCount == 1) {
            mCallback.setAnthologyGridGone();
        } else {
            mCallback.setVideoList(videoSetList.getVideoSet());
            for (int i = 1; i < videoCount; i++) {
                VideoSetList.VideoDateVidEntity entity = videoSetList.getVideoSet().get(i);
                queryYoukuVid(i, entity.getDate(), entity.getVid());
            }
        }
    }

    @Override
    public void onGetVideoSetFailed() {
        final String error = "获取视频信息失败";
        mCallback.onGetInfoFailed(error);
    }

    @Override
    public void onGetRelatedVideoListSuccess(List<RelatedVideoList.RelatedVideoEntity> relatedVideoEntityList) {
        mCallback.setRelatedVideoList(relatedVideoEntityList);
    }

    @Override
    public void onGetRelatedVideoListFailed() {

    }

    @Override
    public void onGetDetailAndRelatedVideoList(VideoDetailInfo videoDetailInfo, List<RelatedVideoList.RelatedVideoEntity> relatedVideoEntityList) {
        if (videoDetailInfo != null) {
            String watchedCount = NumberConversion.bigNumber(videoDetailInfo.getView_count()) + "次播放";
            String upCount = NumberConversion.bigNumber(videoDetailInfo.getUp_count());
            String downCount = NumberConversion.bigNumber(videoDetailInfo.getDown_count());
            String title = videoDetailInfo.getTitle();
            String publishTime = "发布于 " + videoDetailInfo.getPublished();
            mCallback.setVideoDetail(title, publishTime, watchedCount, upCount, downCount);
        }

        if (relatedVideoEntityList != null && relatedVideoEntityList.size() > 0) {
            mCallback.setRelatedVideoList(relatedVideoEntityList);
        } else {
            mCallback.setNoRelatedVideo();
        }
        mCallback.hideProgressBar();
    }

    @Override
    public void onGetVideoDetailSuccess(VideoDetailInfo videoDetailInfo) {
        String watchedCount = NumberConversion.bigNumber(videoDetailInfo.getView_count()) + "次播放";
        String upCount = NumberConversion.bigNumber(videoDetailInfo.getUp_count());
        String downCount = NumberConversion.bigNumber(videoDetailInfo.getDown_count());
        String title = videoDetailInfo.getTitle();
        String publishTime = "发布于 " + videoDetailInfo.getPublished();
        mCallback.setVideoDetail(title, publishTime, watchedCount, upCount, downCount);
    }

    @Override
    public void onGetVideoDetailFailed() {

    }

    @Override
    public void onGetYoukuVidSuccess(int index, String vid) {
        mCallback.setYoukuVid(false, index, vid);
    }

    @Override
    public void onGetYoukuVidFailed() {

    }
}
