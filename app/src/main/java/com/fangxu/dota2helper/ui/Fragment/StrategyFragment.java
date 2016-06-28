package com.fangxu.dota2helper.ui.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.presenter.IStrategyView;
import com.fangxu.dota2helper.presenter.StrategyPresenter;
import com.fangxu.dota2helper.ui.Activity.ArticalDetailActivity;
import com.fangxu.dota2helper.ui.adapter.CommonRecyclerAdapter;
import com.fangxu.dota2helper.ui.adapter.StrategyAdapter;
import com.fangxu.dota2helper.util.NavUtil;
import com.fangxu.dota2helper.util.ToastUtil;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/11.
 */
public class StrategyFragment extends RefreshBaseFragment implements IStrategyView {
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
        mAdapter = new StrategyAdapter(getActivity());
        mAdapter.setItemClickListener(new CommonRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                StrategyList.StrategyEntity entity = mAdapter.getItem(position);
                ArticalDetailActivity.toNewsDetailActivity(getActivity(), entity.getDate(), entity.getNid());
            }

            @Override
            public void onHeaderClick() {

            }

            @Override
            public void onFooterClick() {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void init() {
        int position = FragmentPagerItem.getPosition(getArguments());
        String strategyType = NavUtil.strategyTypeList[position];
        mPresenter = new StrategyPresenter(getActivity(), this, strategyType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected void onFragmentFirstVisible() {
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
    public void setStrategyList(List<StrategyList.StrategyEntity> strategyEntityList, boolean append) {
        mAdapter.updateData(strategyEntityList, append);
    }

    @Override
    public void showNoMoreToast() {
        ToastUtil.showToast(getActivity(), R.string.no_more_content);
    }

    @Override
    public void setRefreshFailed(boolean loadMore) {
        ToastUtil.showToast(getActivity(), R.string.load_fail);
    }

    @Override
    public void hideProgress(boolean loadMore) {
        if (loadMore) {
            mSwipeRefresh.setLoadingMore(false);
        } else {
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
