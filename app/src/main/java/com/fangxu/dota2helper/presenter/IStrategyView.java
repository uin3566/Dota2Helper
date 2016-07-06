package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.bean.StrategyList;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/17.
 */
public interface IStrategyView {
    void setStrategyList(List<StrategyList.StrategyEntity> strategyEntityList, boolean append);
    void setRefreshFailed(boolean loadMore);
    void hideProgress(boolean loadMore);
    void showNoMoreToast();
    void onCacheLoaded();
}
