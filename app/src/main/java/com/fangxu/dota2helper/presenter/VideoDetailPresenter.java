package com.fangxu.dota2helper.presenter;

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

    public void queryYoukuVid(int index, String date, String vid) {
        mInteractor.queryYoukuVid(index, date, vid);
    }

    public void queryYoukuVideoDetail(String vid) {
        mInteractor.queryYoukuVideoDetail(vid);
    }

    public void queryRelatedYoukuVideo(String vid) {
        mInteractor.queryRelatedVideoList(vid);
    }

    @Override
    public void onGetVideoSetSuccess(VideoSetList videoSetList) {
        int isValid = videoSetList.getIsvalid();
        if (isValid == 1) {
            mCallback.setYoukuVid(true, 0, videoSetList.getYoukuvid());
            int videoCount = videoSetList.getList().size();
            if (videoCount == 1) {
                mCallback.setAnthologyGridGone();
            } else {
                mCallback.setVideoList(videoSetList.getList());
                for (int i = 1; i< videoCount; i++) {
                    VideoSetList.VideoDateVidEntity entity = videoSetList.getList().get(i);
                    queryYoukuVid(i, entity.getDate(), entity.getVid());
                }
            }
        } else {
            final String invalid = "暂不支持该类视频";
            mCallback.onVideoInvalid(invalid);
        }
    }

    @Override
    public void onGetRelatedVideoListSuccess(List<RelatedVideoList.RelatedVideoEntity> relatedVideoEntityList) {
        mCallback.setRelatedVideoList(relatedVideoEntityList);
    }

    @Override
    public void onGetRelatedVideoListFailed() {

    }

    @Override
    public void onGetVideoSetFailed() {
        final String error = "获取视频信息失败";
        mCallback.onGetInfoFailed(error);
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
