package com.fangxu.dota2helper.interactor;

import android.app.Activity;
import android.util.Log;

import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.network.AppNetWork;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenov0 on 2016/4/17.
 */
public class StrategyInteractor extends BaseInteractor{
    private static final String TAG = "test task id";
    private StrategyCallback mCallback;
    private int mNextListId;

    public StrategyInteractor(Activity activity, StrategyCallback callback) {
        mCallback = callback;
        mCompositeSubscription = RxCenter.INSTANCE.getCompositeSubscription(activity.getTaskId());
        Log.i(TAG, activity.getClass().getName() + ", taskId=" + activity.getTaskId());
    }

    public void queryStrategies(String type) {
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi()
                .refreshStrategies(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StrategyList>() {
                    @Override
                    public void call(StrategyList strategyList) {
                        mNextListId = strategyList.getNextListId();
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
        mCompositeSubscription.add(AppNetWork.INSTANCE.getNewsApi()
                .loadMoreStrategies(type, mNextListId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StrategyList>() {
                    @Override
                    public void call(StrategyList strategyList) {
                        mNextListId = strategyList.getNextListId();
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
