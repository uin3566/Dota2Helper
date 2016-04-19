package com.fangxu.dota2helper.ui.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.presenter.IStrategyView;
import com.fangxu.dota2helper.presenter.StrategyPresenter;
import com.fangxu.dota2helper.ui.Activity.NewsDetailActivity;
import com.fangxu.dota2helper.ui.adapter.StrategyAdapter;
import com.fangxu.dota2helper.util.NavUtil;
import com.fangxu.dota2helper.util.ToastUtil;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/11.
 */
public class StrategyFragment extends RefreshBaseFragment implements
        StrategyAdapter.ItemClickListener, IStrategyView {
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private StrategyAdapter mAdapter;
    private StrategyPresenter mPresenter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_swipe_to_load;
    }

    @Override
    public void initView(View view) {
        mAdapter = new StrategyAdapter(getActivity(), this);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void init() {
        int position = FragmentPagerItem.getPosition(getArguments());
        String strategyType = NavUtil.strategyTypeList[position];
        mPresenter = new StrategyPresenter(this, strategyType);
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
    public void setStrategyList(List<StrategyList.StrategyEntity> strategyEntityList, boolean append) {
        if (append) {
            mSwipeRefresh.setLoadingMore(false);
        } else {
            mSwipeRefresh.setRefreshing(false);
        }
        mAdapter.updateData(strategyEntityList, append);
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
