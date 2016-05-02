package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.content.Context;

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
public class NewsInteractor {
    private NewsCallback mCallback;
    private int mNextNewsListId;
    private int mNextUpdatesListId;
    private CompositeSubscription mCompositeSubscription;

    public NewsInteractor(Activity activity, NewsCallback callback) {
        mCallback = callback;
        mCompositeSubscription = RxCenter.INSTANCE.getCompositeSubscription(activity.getTaskId());
    }

    public void queryNews() {
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi().refreshNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mNextNewsListId = newsList.getNextListId();
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
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi().loadMoreNews(mNextNewsListId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mNextNewsListId = newsList.getNextListId();
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
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi().refreshUpdates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mNextUpdatesListId = newsList.getNextListId();
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
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi().loadMoreUpdates(mNextUpdatesListId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mNextUpdatesListId = newsList.getNextListId();
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
