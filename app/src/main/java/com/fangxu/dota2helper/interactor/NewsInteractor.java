package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.util.Log;

import com.fangxu.dota2helper.MyApp;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.callback.NewsCallback;
import com.fangxu.dota2helper.greendao.GreenNews;
import com.fangxu.dota2helper.greendao.GreenNewsDao;
import com.fangxu.dota2helper.greendao.GreenUpdate;
import com.fangxu.dota2helper.greendao.GreenUpdateDao;
import com.fangxu.dota2helper.network.AppNetWork;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsInteractor extends BaseInteractor {
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

    public void getCachedNews() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(Observable.create(new Observable.OnSubscribe<NewsList>() {
            @Override
            public void call(Subscriber<? super NewsList> subscriber) {
                NewsList newsList = getGreenDaoNews();
                subscriber.onNext(newsList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        if (newsList != null) {
                            mCallback.onGetCache(newsList.getBanner(), newsList.getNews(), false);
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

    private NewsList getGreenDaoNews() {
        NewsList newsList = null;
        GreenNewsDao greenNewsDao = MyApp.getGreenDaoHelper().getSession().getGreenNewsDao();
        List<GreenNews> list = greenNewsDao.queryBuilder().build().list();
        if (list != null && !list.isEmpty()) {
            newsList = MyApp.getGson().fromJson(list.get(0).getNewslistjson(), NewsList.class);
        }
        return newsList;
    }

    private void cacheGreenDaoNews(NewsList newsList) {
        GreenNewsDao greenNewsDao = MyApp.getGreenDaoHelper().getSession().getGreenNewsDao();
        greenNewsDao.queryBuilder().buildDelete().executeDeleteWithoutDetachingEntities();
        String jsonData = MyApp.getGson().toJson(newsList);
        GreenNews greenNews = new GreenNews(null, jsonData);
        greenNewsDao.insert(greenNews);
    }

    public void queryNews() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(AppNetWork.INSTANCE.getNewsApi().refreshNews()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        cacheGreenDaoNews(newsList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mLastNewsNid = newsList.getNews().get(newsList.getNews().size() - 1).getNid();
                        mCallback.onUpdateSuccessed(newsList.getNews(), false);
                        mCallback.onGetBanner(newsList.getBanner());
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

    public void getCachedUpdates() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(Observable.create(new Observable.OnSubscribe<NewsList>() {
            @Override
            public void call(Subscriber<? super NewsList> subscriber) {
                NewsList newsList = getGreenDaoUpdates();
                subscriber.onNext(newsList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        if (newsList != null) {
                            mCallback.onGetCache(newsList.getBanner(), newsList.getNews(), true);
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

    public NewsList getGreenDaoUpdates() {
        NewsList newsList = null;
        GreenUpdateDao greenUpdateDao = MyApp.getGreenDaoHelper().getSession().getGreenUpdateDao();
        List<GreenUpdate> greenUpdates = greenUpdateDao.queryBuilder().build().list();
        if (greenUpdates != null && !greenUpdates.isEmpty()) {
            newsList = MyApp.getGson().fromJson(greenUpdates.get(0).getUpdatelistjson(), NewsList.class);
        }
        return newsList;
    }

    public void cacheGreenDaoUpdates(NewsList newsList) {
        GreenUpdateDao greenUpdateDao = MyApp.getGreenDaoHelper().getSession().getGreenUpdateDao();
        greenUpdateDao.queryBuilder().buildDelete().executeDeleteWithoutDetachingEntities();
        String jsonData = MyApp.getGson().toJson(newsList);
        GreenUpdate greenUpdate = new GreenUpdate(null, jsonData);
        greenUpdateDao.insert(greenUpdate);
    }

    public void queryUpdates() {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsTaskId).add(AppNetWork.INSTANCE.getNewsApi().refreshUpdates()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        cacheGreenDaoUpdates(newsList);
                    }
                })
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
