package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.util.Log;

import com.fangxu.dota2helper.MyApp;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoDetailInfo;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.callback.VideoPlayerCallback;
import com.fangxu.dota2helper.network.AppNetWork;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoPlayerInteractor extends BaseInteractor {
    private static final String TAG = "test task id";
    private VideoPlayerCallback mCallback;
    private VideoDetailInfo mDetailInfo = null;
    private List<RelatedVideoList.RelatedVideoEntity> mVideoList = null;

    public VideoPlayerInteractor(Activity activity, VideoPlayerCallback videoPlayerCallback) {
        mCallback = videoPlayerCallback;
        Log.i(TAG, activity.getClass().getName() + ", taskId=" + activity.getTaskId());
    }

    @Override
    public void destroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.videoDetailTaskId);
    }

    public void queryVideoSetInfo(String date, String vid) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoDetailTaskId).add(AppNetWork.INSTANCE.getNewsApi()
                .getVideoSetInfo(date, vid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoSetList>() {
                    @Override
                    public void call(VideoSetList videoSetList) {
                        mCallback.onGetVideoSetSuccess(videoSetList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onGetVideoSetFailed();
                    }
                }));
    }

    public void queryYoukuVid(final int index, String date, String vid) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoDetailTaskId).add(AppNetWork.INSTANCE.getDetailsApi()
                .getYoukuVid(date, vid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mCallback.onGetYoukuVidSuccess(index, s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onGetYoukuVidFailed();
                    }
                }));
    }

    public void queryRelatedVideoList(String vid) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoDetailTaskId).add(AppNetWork.INSTANCE.getYoukuApi()
                .getRelatedVideoList(MyApp.getYoukuClientId(), vid, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RelatedVideoList>() {
                    @Override
                    public void call(RelatedVideoList relatedVideoList) {
                        mCallback.onGetRelatedVideoListSuccess(relatedVideoList.getVideos());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onGetRelatedVideoListFailed();
                    }
                }));
    }

    public void queryDetailAndRelated(String vid) {
        mDetailInfo = null;
        mVideoList = null;
        Observable o1 = AppNetWork.INSTANCE.getYoukuApi().getVideoDetailInfo(MyApp.getYoukuClientId(), vid);
        Observable o2 = AppNetWork.INSTANCE.getYoukuApi().getRelatedVideoList(MyApp.getYoukuClientId(), vid, 20);
        Subscription subscription = Observable.mergeDelayError(o1, o2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        mCallback.onGetDetailAndRelatedVideoList(mDetailInfo, mVideoList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallback.onGetDetailAndRelatedVideoList(mDetailInfo, mVideoList);
                    }

                    @Override
                    public void onNext(Object o) {
                        if (o instanceof VideoDetailInfo) {
                            mDetailInfo = (VideoDetailInfo) o;
                        } else {
                            mVideoList = ((RelatedVideoList) o).getVideos();
                        }
                    }
                });
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoDetailTaskId).add(subscription);
    }
}
