package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.util.Log;

import com.fangxu.dota2helper.MyApp;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.callback.StrategyCallback;
import com.fangxu.dota2helper.greendao.GreenStrategy;
import com.fangxu.dota2helper.greendao.GreenStrategyDao;
import com.fangxu.dota2helper.network.AppNetWork;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenov0 on 2016/4/17.
 */
public class StrategyInteractor extends BaseInteractor{
    private static final String TAG = "test task id";
    private StrategyCallback mCallback;
    private String mLastNid;

    public StrategyInteractor(Activity activity, StrategyCallback callback) {
        mCallback = callback;
        Log.i(TAG, activity.getClass().getName() + ", taskId=" + activity.getTaskId());
    }

    @Override
    public void destroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.strategyTaskId);
    }

    public void getCachedStrategies(final String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.strategyTaskId).add(Observable.create(new Observable.OnSubscribe<StrategyList>() {
            @Override
            public void call(Subscriber<? super StrategyList> subscriber) {
                StrategyList strategyList = getCachedGreenDaoStrategies(type);
                subscriber.onNext(strategyList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<StrategyList>() {
            @Override
            public void call(StrategyList strategyList) {
                if (strategyList != null) {
                    mCallback.onGetCachedStrategies(strategyList.getStrategies());
                } else {
                    mCallback.onCachedStrategiesEmpty();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mCallback.onCachedStrategiesEmpty();
            }
        }));
    }

    private void cacheGreenDaoStrategies(StrategyList strategyList, String type) {
        GreenStrategyDao greenStrategyDao = MyApp.getGreenDaoHelper().getSession().getGreenStrategyDao();
        greenStrategyDao.queryBuilder().where(GreenStrategyDao.Properties.Strategytype.eq(type)).buildDelete().executeDeleteWithoutDetachingEntities();
        String jsonData = MyApp.getGson().toJson(strategyList);
        GreenStrategy greenStrategy = new GreenStrategy(null, jsonData, type);
        greenStrategyDao.insert(greenStrategy);
    }

    private StrategyList getCachedGreenDaoStrategies(String type) {
        StrategyList strategyList = null;
        GreenStrategyDao greenStrategyDao = MyApp.getGreenDaoHelper().getSession().getGreenStrategyDao();
        List<GreenStrategy> list = greenStrategyDao.queryBuilder().where(GreenStrategyDao.Properties.Strategytype.eq(type)).build().list();
        if (list != null && !list.isEmpty()) {
            strategyList = MyApp.getGson().fromJson(list.get(0).getStrategylistjson(), StrategyList.class);
        }
        return strategyList;
    }

    public void queryStrategies(final String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.strategyTaskId).add(AppNetWork.INSTANCE.getNewsApi()
                .refreshStrategies(type)
                .doOnNext(new Action1<StrategyList>() {
                    @Override
                    public void call(StrategyList strategyList) {
                        cacheGreenDaoStrategies(strategyList, type);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StrategyList>() {
                    @Override
                    public void call(StrategyList strategyList) {
                        mLastNid = strategyList.getStrategies().get(strategyList.getStrategies().size() - 1).getNid();
                        mCallback.onUpdateSuccessed(strategyList.getStrategies(), false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(false);
                    }
                }));
    }

    public void queryMoreStrategies(String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.strategyTaskId).add(AppNetWork.INSTANCE.getNewsApi()
                .loadMoreStrategies(type, mLastNid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StrategyList>() {
                    @Override
                    public void call(StrategyList strategyList) {
                        int size = strategyList.getStrategies().size();
                        if (size > 0) {
                            mLastNid = strategyList.getStrategies().get(size - 1).getNid();
                        }
                        mCallback.onUpdateSuccessed(strategyList.getStrategies(), true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onUpdateFailed(true);
                    }
                }));
    }
}
