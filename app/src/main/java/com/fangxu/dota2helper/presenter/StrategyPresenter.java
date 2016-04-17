package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.interactor.StrategyCallback;
import com.fangxu.dota2helper.interactor.StrategyInteractor;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/17.
 */
public class StrategyPresenter implements StrategyCallback{
    private IStrategyView mCallback;
    private StrategyInteractor mInteractor;
    private String mType;

    public StrategyPresenter(IStrategyView iStrategyView, String type) {
        mCallback = iStrategyView;
        mType = type;
        mInteractor = new StrategyInteractor(this);
    }

    public void doRefresh() {
        mInteractor.queryStrategies(mType);
    }

    public void doLoadMore() {
        mInteractor.queryMoreStrategies(mType);
    }

    @Override
    public void onUpdateSuccessed(List<StrategyList.StrategyEntity> strategyEntityList, boolean loadmore) {
        mCallback.setStrategyList(strategyEntityList, loadmore);
    }

    @Override
    public void onUpdateFailed(boolean loadmore) {
        mCallback.setRefreshFailed(loadmore);
    }
}
