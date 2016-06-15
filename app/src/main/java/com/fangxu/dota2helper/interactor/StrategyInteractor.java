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
    private String mLastNid;

    public StrategyInteractor(Activity activity, StrategyCallback callback) {
        mCallback = callback;
        Log.i(TAG, activity.getClass().getName() + ", taskId=" + activity.getTaskId());
    }

    @Override
    public void destroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.strategyTaskId);
    }

    public void queryStrategies(String type) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.strategyTaskId).add(AppNetWork.INSTANCE.getNewsApi()
                .refreshStrategies(type)
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
