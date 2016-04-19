package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.VideoList;
import com.fangxu.dota2helper.network.AppNetWork;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoInteractor {
    private VideoCallback mCallback;
    private int mNextListId;

    public VideoInteractor(VideoCallback callback) {
        mCallback = callback;
    }

    public void queryVideos(String type) {
        AppNetWork.INSTANCE.getNewsApi()
                .refreshVideos(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoList>() {
                    @Override
                    public void call(VideoList videoList) {
                        mNextListId = videoList.getNextListId();
                        mCallback.onUpdateSuccessed(videoList.getVideos(), false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(false);
                    }
                });
    }

    public void queryMoreVideos(String type) {
        AppNetWork.INSTANCE.getNewsApi()
                .loadMoreVideos(type, mNextListId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoList>() {
                    @Override
                    public void call(VideoList videoList) {
                        mNextListId = videoList.getNextListId();
                        mCallback.onUpdateSuccessed(videoList.getVideos(), true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(true);
                    }
                });
    }
}
