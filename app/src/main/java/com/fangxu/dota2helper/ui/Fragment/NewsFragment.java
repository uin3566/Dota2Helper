package com.fangxu.dota2helper.ui.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.presenter.INewsView;
import com.fangxu.dota2helper.presenter.NewsPresenter;
import com.fangxu.dota2helper.ui.adapter.NewsAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/3.
 */
public class NewsFragment extends BaseFragment implements INewsView{
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private NewsAdapter mAdapter;

    private NewsPresenter mPresenter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_news;
    }

    @Override
    public void init() {
        mPresenter = new NewsPresenter(this);
    }

    @Override
    public void initView() {
        mAdapter = new NewsAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.doRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.doLoadMore();
    }

    @Override
    public void setNewsList(List<NewsList.NewsEntity> newsEntityList) {
        mSwipeRefresh.setRefreshing(false);
        mAdapter.updateData(newsEntityList);
    }

    @Override
    public void setRefreshFailed() {
        mSwipeRefresh.setRefreshing(false);
    }
}
