package com.fangxu.dota2helper.util;

import android.util.Log;

import com.fangxu.dota2helper.MyApp;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.callback.WatchedVideoDeleteCallback;
import com.fangxu.dota2helper.eventbus.BusProvider;
import com.fangxu.dota2helper.eventbus.WatchedVideoGetEvent;
import com.fangxu.dota2helper.greendao.GreenWatchedVideo;
import com.fangxu.dota2helper.greendao.GreenWatchedVideoDao;
import com.fangxu.dota2helper.interactor.TaskIds;

import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/19.
 */
public enum VideoCacheManager {
    INSTANCE;

    private static final String TAG = "VideoCacheManager";

    VideoCacheManager() {

    }

    public void cacheWatchedVideo(final String ykvid, final String background, final String title,
                                  final int videoDuration, final int videoPlaytime, final boolean isEnded) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GreenWatchedVideoDao greenWatchedVideoDao = MyApp.getGreenDaoHelper().getSession().getGreenWatchedVideoDao();
                GreenWatchedVideo greenWatchedVideo = new GreenWatchedVideo(ykvid, background, title
                        , System.currentTimeMillis(), videoDuration, videoPlaytime, isEnded);
                greenWatchedVideoDao.insertOrReplace(greenWatchedVideo);
                Log.i(TAG, "cache video:" + title);
            }
        }).start();
    }

    public void deleteSelectedVideo(final Set<String> keySet, final WatchedVideoDeleteCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GreenWatchedVideoDao greenWatchedVideoDao = MyApp.getGreenDaoHelper().getSession().getGreenWatchedVideoDao();
                greenWatchedVideoDao.deleteByKeyInTx(keySet);
                if (callback != null) {
                    callback.onSelectVideoDeleted();
                }
            }
        }).start();
    }

    public void getWatchedVideo() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.videoCacheTaskId).add(Observable.create(new Observable.OnSubscribe<List<GreenWatchedVideo>>() {
            @Override
            public void call(Subscriber<? super List<GreenWatchedVideo>> subscriber) {
                subscriber.onNext(queryHistoryVideo());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<GreenWatchedVideo>>() {
                    @Override
                    public void call(List<GreenWatchedVideo> greenWatchedVideos) {
                        BusProvider.getInstance().post(new WatchedVideoGetEvent(true, greenWatchedVideos));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        BusProvider.getInstance().post(new WatchedVideoGetEvent(false, null));
                    }
                }));
    }

    private List<GreenWatchedVideo> queryHistoryVideo() {
        GreenWatchedVideoDao greenWatchedVideoDao = MyApp.getGreenDaoHelper().getSession().getGreenWatchedVideoDao();
        return greenWatchedVideoDao.queryBuilder().orderDesc(GreenWatchedVideoDao.Properties.Videowatchtime).build().list();
    }
}
