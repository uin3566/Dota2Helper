package com.fangxu.dota2helper.callback;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.bean.StrategyList;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/17.
 */
public interface StrategyCallback {
    void onGetCachedStrategies(List<StrategyList.StrategyEntity> strategyEntityList);
    void onCachedStrategiesEmpty();
    void onUpdateSuccessed(List<StrategyList.StrategyEntity> strategyEntityList, boolean loadmore);
    void onUpdateFailed(boolean loadmore);
}
