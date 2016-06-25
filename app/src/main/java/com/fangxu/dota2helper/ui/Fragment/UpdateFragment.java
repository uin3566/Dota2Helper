package com.fangxu.dota2helper.ui.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.presenter.INewsView;
import com.fangxu.dota2helper.presenter.NewsPresenter;
import com.fangxu.dota2helper.ui.Activity.NewsDetailActivity;
import com.fangxu.dota2helper.ui.adapter.CommonRecyclerAdapter;
import com.fangxu.dota2helper.ui.adapter.NewsAdapter;
import com.fangxu.dota2helper.util.ToastUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/4.
 */
public class UpdateFragment extends RefreshBaseFragment implements INewsView{
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private NewsAdapter mAdapter;
    private NewsPresenter mPresenter;

    public static UpdateFragment newInstance() {
        return new UpdateFragment();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_swipe_to_load;
    }

    @Override
    public void init() {
        mPresenter = new NewsPresenter(getActivity(), this);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void initView(View view) {
        mAdapter = new NewsAdapter(getActivity());
        mAdapter.setItemClickListener(new CommonRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NewsList.NewsEntity newsEntity = mAdapter.getItem(position);
                NewsDetailActivity.toNewsDetailActivity(getActivity(), newsEntity.getDate(), newsEntity.getNid());
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
    public void setBanner(List<NewsList.BannerEntity> bannerEntityList) {

    }

    @Override
    public void setNewsList(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        mAdapter.updateData(newsEntityList, append);
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
