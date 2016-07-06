package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.util.Log;

import com.fangxu.dota2helper.MyApp;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.bean.VideoList;
import com.fangxu.dota2helper.greendao.GreenVideo;
import com.fangxu.dota2helper.greendao.GreenVideoDao;
import com.fangxu.dota2helper.network.AppNetWork;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
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

    public void getCachedVideos(final String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoTaskId).add(Observable.create(new Observable.OnSubscribe<VideoList>() {
            @Override
            public void call(Subscriber<? super VideoList> subscriber) {
                VideoList videoList = getCachedGreenDaoVideos(type);
                subscriber.onNext(videoList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<VideoList>() {
            @Override
            public void call(VideoList videoList) {
                if (videoList != null) {
                    mCallback.onGetCachedVideo(videoList.getVideos());
                } else {
                    mCallback.onCacheEmpty();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mCallback.onCacheEmpty();
            }
        }));
    }

    private void cacheGreenDaoVideos(VideoList videoList, String type) {
        GreenVideoDao greenVideoDao = MyApp.getGreenDaoHelper().getSession().getGreenVideoDao();
        greenVideoDao.queryBuilder().where(GreenVideoDao.Properties.Videotype.eq(type)).buildDelete().executeDeleteWithoutDetachingEntities();
        String jsonData = MyApp.getGson().toJson(videoList);
        GreenVideo greenVideo = new GreenVideo(null, jsonData, type);
        greenVideoDao.insert(greenVideo);
    }

    private VideoList getCachedGreenDaoVideos(String type) {
        VideoList videoList = null;
        GreenVideoDao greenVideoDao = MyApp.getGreenDaoHelper().getSession().getGreenVideoDao();
        List<GreenVideo> list = greenVideoDao.queryBuilder().where(GreenVideoDao.Properties.Videotype.eq(type)).build().list();
        if (list != null && !list.isEmpty()) {
            videoList = MyApp.getGson().fromJson(list.get(0).getVideolistjson(), VideoList.class);
        }
        return videoList;
    }

    public void queryVideos(final String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoTaskId).add(AppNetWork.INSTANCE.getNewsApi()
                .refreshVideos(type)
                .doOnNext(new Action1<VideoList>() {
                    @Override
                    public void call(VideoList videoList) {
                        cacheGreenDaoVideos(videoList, type);
                    }
                })
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
                        int size = videoList.getVideos().size();
                        if (size > 0) {
                            mLastVid = videoList.getVideos().get(videoList.getVideos().size() - 1).getVid();
                        }
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
