package com.fangxu.dota2helper.presenter;

import android.app.Activity;
import android.content.Context;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.interactor.NewsCallback;
import com.fangxu.dota2helper.interactor.NewsInteractor;
import com.fangxu.dota2helper.ui.Fragment.NewsFragment;
import com.fangxu.dota2helper.ui.Fragment.UpdateFragment;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsPresenter extends BasePresenter implements NewsCallback{
    public static final int NEWS = 0;
    public static final int UPDATES = 1;

    private INewsView mCallback;
    private int mType;

    public NewsPresenter(Activity activity, INewsView iNewsView) {
        mInteractor = new NewsInteractor(activity, this);
        mCallback = iNewsView;
        if (iNewsView instanceof NewsFragment){
            mType = NEWS;
        } else if (iNewsView instanceof UpdateFragment) {
            mType = UPDATES;
        }
    }

    public void doRefresh() {
        if (mType == NEWS) {
            ((NewsInteractor)mInteractor).queryNews();
        } else {
            ((NewsInteractor)mInteractor).queryUpdates();
        }
    }

    public void doLoadMore() {
        if (mType == NEWS) {
            ((NewsInteractor)mInteractor).queryMoreNews();
        } else {
            ((NewsInteractor)mInteractor).queryMoreUpdates();
        }
    }

    @Override
    public void onUpdateFailed(boolean loadmore) {
        mCallback.setRefreshFailed(loadmore);
        mCallback.hideProgress(loadmore);
    }

    @Override
    public void onUpdateSuccessed(List<NewsList.NewsEntity> newsEntityList, boolean loadmore) {
        mCallback.setNewsList(newsEntityList, loadmore);
        mCallback.hideProgress(loadmore);
    }
}
