package com.fangxu.dota2helper.interactor;

import android.util.Log;

import com.fangxu.dota2helper.MyApp;
import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoDetailInfo;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.network.AppNetWork;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoDetailInteractor {
    private VideoDetailCallback mCallback;

    public VideoDetailInteractor(VideoDetailCallback videoDetailCallback) {
        mCallback = videoDetailCallback;
    }

    public void queryVideoSetInfo(String date, String vid) {
        AppNetWork.INSTANCE.getNewsApi()
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
                });
    }

    public void queryYoukuVideoDetail(String vid) {
        AppNetWork.INSTANCE.getYoukuApi()
                .getVideoDetailInfo(MyApp.getYoukuClientId(), vid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoDetailInfo>() {
                    @Override
                    public void call(VideoDetailInfo videoDetailInfo) {
                        mCallback.onGetVideoDetailSuccess(videoDetailInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onGetVideoDetailFailed();
                    }
                });
    }

    public void queryYoukuVid(final int index, String date, String vid) {
        AppNetWork.INSTANCE.getDetailsApi()
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
                });
    }

    public void queryRelatedVideoList(String vid) {
        AppNetWork.INSTANCE.getYoukuApi()
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
                });
    }
}
