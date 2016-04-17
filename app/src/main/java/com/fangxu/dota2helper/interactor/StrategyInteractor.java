package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.network.AppNetWork;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenov0 on 2016/4/17.
 */
public class StrategyInteractor {
    private StrategyCallback mCallback;
    private int mNextListId;

    public StrategyInteractor(StrategyCallback callback) {
        mCallback = callback;
    }

    public void queryStrategies(String type) {
        AppNetWork.INSTANCE.getNewsApi()
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
                });

    }

    public void queryMoreStrategies(String type) {
        AppNetWork.INSTANCE.getNewsApi()
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
                });
    }
}
