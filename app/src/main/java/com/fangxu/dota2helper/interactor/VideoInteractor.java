package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.util.Log;

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
public class VideoInteractor extends BaseInteractor{
    private static final String TAG = "test task id";
    private VideoCallback mCallback;
    private String mLastVid;

    public VideoInteractor(Activity activity, VideoCallback callback) {
        mCallback = callback;
        Log.i(TAG, activity.getClass().getName() + ", taskId=" + activity.getTaskId());
    }

    @Override
    public void destroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.videoTaskId);
    }

    public void queryVideos(String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoTaskId).add(AppNetWork.INSTANCE.getNewsApi()
                .refreshVideos(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoList>() {
                    @Override
                    public void call(VideoList videoList) {
                        mLastVid = videoList.getVideos().get(videoList.getVideos().size() - 1).getVid();
                        mCallback.onUpdateSuccessed(videoList.getVideos(), false);
                        Log.i(TAG, "queryVideos success");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(false);
                        Log.i(TAG, "queryVideos failed");
                    }
                }));
    }

    public void queryMoreVideos(String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoTaskId).add(AppNetWork.INSTANCE.getNewsApi()
                .loadMoreVideos(type, mLastVid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoList>() {
                    @Override
                    public void call(VideoList videoList) {
                        mLastVid = videoList.getVideos().get(videoList.getVideos().size() - 1).getVid();
                        mCallback.onUpdateSuccessed(videoList.getVideos(), true);
                        Log.i(TAG, "queryMoreVideos success");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(true);
                        Log.i(TAG, "queryMoreVideos failed");
                    }
                }));
    }
}
