package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.NewsList;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/9.
 */
public interface NewsCallback {
    void onRefreshSuccessed(List<NewsList.NewsEntity> newsEntityList, boolean loadmore);
    void onRefreshFailed();
}
