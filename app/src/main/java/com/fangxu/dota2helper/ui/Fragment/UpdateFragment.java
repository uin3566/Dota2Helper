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
 * Created by Xuf on 2016/4/4.
 */
public class UpdateFragment extends BaseFragment implements INewsView, NewsAdapter.ItemClickListener{
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private NewsAdapter mAdapter;
    private NewsPresenter mPresenter;

    public static UpdateFragment newInstance() {
        return new UpdateFragment();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_update;
    }

    @Override
    public void init() {
        mPresenter = new NewsPresenter(this);
    }

    @Override
    public void initView() {
        mAdapter = new NewsAdapter(getActivity(), this);
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
    public void setNewsList(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        mSwipeRefresh.setRefreshing(false);
        mAdapter.updateData(newsEntityList, false);
    }

    @Override
    public void setRefreshFailed(boolean loadMore) {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(String url) {

    }
}
