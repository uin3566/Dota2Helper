package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.network.AppNetWork;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsInteractor extends BaseInteractor{
    private static final String TAG = "test task id";
    private NewsCallback mCallback;
    private String mLastNewsNid;
    private String mLastUpdatesNid;

    public NewsInteractor(Activity activity, NewsCallback callback) {
        mCallback = callback;
        Log.i(TAG, activity.getClass().getName() + ", taskId=" + activity.getTaskId());
    }

    @Override
    public void destroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.newsTaskId);
    }

    public void queryNews() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(AppNetWork.INSTANCE.getNewsApi().refreshNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mLastNewsNid = newsList.getNews().get(newsList.getNews().size() - 1).getNid();
                        mCallback.onUpdateSuccessed(newsList.getNews(), false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(false);
                    }
                }));
    }

    public void queryMoreNews() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(AppNetWork.INSTANCE.getNewsApi().loadMoreNews(mLastNewsNid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        int size = newsList.getNews().size();
                        if (size > 0) {
                            mLastNewsNid = newsList.getNews().get(size - 1).getNid();
                        }
                        mCallback.onUpdateSuccessed(newsList.getNews(), true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(true);
                    }
                }));
    }

    public void queryUpdates() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(AppNetWork.INSTANCE.getNewsApi().refreshUpdates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mLastUpdatesNid = newsList.getNews().get(newsList.getNews().size() - 1).getNid();
                        mCallback.onUpdateSuccessed(newsList.getNews(), false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(false);
                    }
                }));
    }

    public void queryMoreUpdates() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(AppNetWork.INSTANCE.getNewsApi().loadMoreUpdates(mLastUpdatesNid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        int size = newsList.getNews().size();
                        if (size > 0) {
                            mLastUpdatesNid = newsList.getNews().get(size - 1).getNid();
                        }
                        mCallback.onUpdateSuccessed(newsList.getNews(), true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(true);
                    }
                }));
    }
}
