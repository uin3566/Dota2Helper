package com.fangxu.dota2helper.interactor;

import android.app.Activity;

import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.bean.VideoList;
import com.fangxu.dota2helper.network.AppNetWork;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoInteractor {
    private VideoCallback mCallback;
    private String mLastVid;
    private CompositeSubscription mCompositeSubscription;

    public VideoInteractor(Activity activity, VideoCallback callback) {
        mCallback = callback;
        mCompositeSubscription = RxCenter.INSTANCE.getCompositeSubscription(activity.getTaskId());
    }

    public void queryVideos(String type) {
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi()
                .refreshVideos(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoList>() {
                    @Override
                    public void call(VideoList videoList) {
                        mLastVid = videoList.getVideos().get(videoList.getVideos().size() - 1).getVid();
                        mCallback.onUpdateSuccessed(videoList.getVideos(), false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(false);
                    }
                }));
    }

    public void queryMoreVideos(String type) {
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi()
                .loadMoreVideos(type, mLastVid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoList>() {
                    @Override
                    public void call(VideoList videoList) {
                        mLastVid = videoList.getVideos().get(videoList.getVideos().size() - 1).getVid();
                        mCallback.onUpdateSuccessed(videoList.getVideos(), true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(true);
                    }
                }));
    }
}
