package com.fangxu.dota2helper.presenter;

import android.app.Activity;

import com.fangxu.dota2helper.bean.VideoList;
import com.fangxu.dota2helper.interactor.VideoCallback;
import com.fangxu.dota2helper.interactor.VideoInteractor;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoPresenter extends BasePresenter implements VideoCallback{
    private String mType;
    private IVideoView mCallback;

    public VideoPresenter(Activity activity, IVideoView iVideoView, String type) {
        mType = type;
        mCallback = iVideoView;
        mInteractor = new VideoInteractor(activity, this);
    }

    @Override
    public void destroy() {
        mInteractor.destroy();
    }

    public void doRefresh() {
        ((VideoInteractor)mInteractor).queryVideos(mType);
    }

    public void doLoadMore() {
        ((VideoInteractor)mInteractor).queryMoreVideos(mType);
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
