package com.fangxu.dota2helper.presenter;

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
}
