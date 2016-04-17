package com.fangxu.dota2helper.ui.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.presenter.INewsView;
import com.fangxu.dota2helper.presenter.NewsPresenter;
import com.fangxu.dota2helper.ui.Activity.NewsDetailActivity;
import com.fangxu.dota2helper.ui.adapter.NewsAdapter;
import com.fangxu.dota2helper.util.ToastUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/3.
 */
public class NewsFragment extends RefreshBaseFragment implements INewsView, NewsAdapter.ItemClickListener{
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private NewsAdapter mAdapter;

    private NewsPresenter mPresenter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_swipe_to_load;
    }

    @Override
    public void init() {
        mPresenter = new NewsPresenter(this);
        setRetainInstance(true);
    }

    @Override
    public void initView(View view) {
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
        if (append) {
            mSwipeRefresh.setLoadingMore(false);
        } else {
            mSwipeRefresh.setRefreshing(false);
        }
        mAdapter.updateData(newsEntityList, append);
    }

    @Override
    public void setRefreshFailed(boolean loadMore) {
        if (loadMore) {
            mSwipeRefresh.setLoadingMore(false);
        } else {
            mSwipeRefresh.setRefreshing(false);
        }
        ToastUtil.showToast(getActivity(), R.string.load_fail);
    }

    @Override
    public void onItemClick(String date, String nid) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.NEWS_DATE, date);
        intent.putExtra(NewsDetailActivity.NEWS_NID, nid);
        startActivity(intent);
    }
}
