package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.NewsList;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/9.
 */
public interface INewsView {
    void setNewsList(List<NewsList.NewsEntity> newsEntityList, boolean append);
    void setRefreshFailed();
}
