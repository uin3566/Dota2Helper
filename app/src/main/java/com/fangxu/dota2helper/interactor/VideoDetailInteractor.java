package com.fangxu.dota2helper.interactor;

import android.util.Log;

import com.fangxu.dota2helper.MyApp;
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
                        Log.i("fjdskl", "success");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("fjdskl", "exception");
                    }
                });
    }

    public void queryYoukuVid(String date, String vid) {

    }
}
