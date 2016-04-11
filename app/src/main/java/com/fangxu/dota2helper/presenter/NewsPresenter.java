package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.interactor.NewsCallback;
import com.fangxu.dota2helper.interactor.NewsInteractor;
import com.fangxu.dota2helper.ui.Fragment.NewsFragment;
import com.fangxu.dota2helper.ui.Fragment.UpdateFragment;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsPresenter implements NewsCallback{
    public static final int NEWS = 0;
    public static final int UPDATES = 1;

    private NewsInteractor mInteractor;
    private INewsView mCallback;
    private int mType;

    public NewsPresenter(INewsView iNewsView) {
        mInteractor = new NewsInteractor(this);
        mCallback = iNewsView;
        if (iNewsView instanceof NewsFragment){
            mType = NEWS;
        } else if (iNewsView instanceof UpdateFragment) {
            mType = UPDATES;
        }
    }

    public void doRefresh() {
        if (mType == NEWS) {
            mInteractor.queryNews();
        } else {
            mInteractor.queryUpdates();
        }
    }

    public void doLoadMore() {
        if (mType == NEWS) {
            mInteractor.queryMoreNews();
        } else {
            mInteractor.queryMoreUpdates();
        }
    }

    @Override
    public void onRefreshFailed() {
        mCallback.setRefreshFailed();
    }

    @Override
    public void onRefreshSuccessed(List<NewsList.NewsEntity> newsEntityList, boolean loadmore) {
        mCallback.setNewsList(newsEntityList, loadmore);
    }
}
