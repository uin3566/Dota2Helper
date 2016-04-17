package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.network.AppNetWork;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsInteractor {
    private NewsCallback mCallback;
    private int mNextNewsListId;
    private int mNextUpdatesListId;

    public NewsInteractor(NewsCallback callback) {
        mCallback = callback;
    }

    public void queryNews() {
        AppNetWork.INSTANCE.getNewsApi().refreshNews()
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
                });
    }

    public void queryMoreNews() {
        AppNetWork.INSTANCE.getNewsApi().loadMoreNews(mNextNewsListId)
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
                });
    }

    public void queryUpdates() {
        AppNetWork.INSTANCE.getNewsApi().refreshUpdates()
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
                });
    }

    public void queryMoreUpdates() {
        AppNetWork.INSTANCE.getNewsApi().loadMoreUpdates(mNextUpdatesListId)
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
                });
    }
}
