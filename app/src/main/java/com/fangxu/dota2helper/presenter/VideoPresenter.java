package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.VideoList;
import com.fangxu.dota2helper.interactor.VideoCallback;
import com.fangxu.dota2helper.interactor.VideoInteractor;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoPresenter implements VideoCallback{
    private String mType;
    private VideoInteractor mInteractor;
    private IVideoView mCallback;

    public VideoPresenter(IVideoView iVideoView, String type) {
        mType = type;
        mCallback = iVideoView;
        mInteractor = new VideoInteractor(this);
    }

    public void doRefresh() {
        mInteractor.queryVideos(mType);
    }

    public void doLoadMore() {
        mInteractor.queryMoreVideos(mType);
    }

    @Override
    public void onUpdateSuccessed(List<VideoList.VideoEntity> videoEntityList, boolean loadmore) {
        mCallback.setVideoList(videoEntityList, loadmore);
        mCallback.hideProgress(loadmore);
    }

    @Override
    public void onUpdateFailed(boolean loadmore) {
        mCallback.setRefreshFailed(loadmore);
        mCallback.hideProgress(loadmore);
    }
}
