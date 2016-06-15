package com.fangxu.dota2helper.presenter;

import android.app.Activity;

import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.interactor.StrategyCallback;
import com.fangxu.dota2helper.interactor.StrategyInteractor;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/17.
 */
public class StrategyPresenter extends BasePresenter implements StrategyCallback{
    private IStrategyView mCallback;
    private String mType;

    public StrategyPresenter(Activity activity, IStrategyView iStrategyView, String type) {
        mCallback = iStrategyView;
        mType = type;
        mInteractor = new StrategyInteractor(activity, this);
    }

    public void doRefresh() {
        ((StrategyInteractor)mInteractor).queryStrategies(mType);
    }

    public void doLoadMore() {
        ((StrategyInteractor)mInteractor).queryMoreStrategies(mType);
    }

    @Override
    public void onUpdateSuccessed(List<StrategyList.StrategyEntity> strategyEntityList, boolean loadmore) {
        mCallback.hideProgress(loadmore);
        if (strategyEntityList.isEmpty()) {
            mCallback.showNoMoreToast();
        } else {
            mCallback.setStrategyList(strategyEntityList, loadmore);
        }
    }

    @Override
    public void onUpdateFailed(boolean loadmore) {
        mCallback.setRefreshFailed(loadmore);
        mCallback.hideProgress(loadmore);
    }
}
