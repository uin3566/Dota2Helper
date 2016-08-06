package com.fangxu.dota2helper.ui.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.eventbus.BannerItemClickEvent;
import com.fangxu.dota2helper.eventbus.BusProvider;
import com.fangxu.dota2helper.eventbus.NewsFragmentSelectionEvent;
import com.fangxu.dota2helper.callback.INewsView;
import com.fangxu.dota2helper.presenter.NewsPresenter;
import com.fangxu.dota2helper.ui.Activity.NewsDetailActivity;
import com.fangxu.dota2helper.ui.adapter.CommonRecyclerAdapter;
import com.fangxu.dota2helper.ui.adapter.NewsAdapter;
import com.fangxu.dota2helper.util.ToastUtil;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/3.
 */
public class NewsFragment extends RefreshBaseFragment implements INewsView, CommonRecyclerAdapter.ItemClickListener {
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private NewsAdapter mAdapter;
    private NewsPresenter mPresenter;

    private boolean mSelected;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_swipe_to_load;
    }

    @Override
    public void init() {
        BusProvider.getInstance().register(this);
        mPresenter = new NewsPresenter(getActivity(), this);
        setRetainInstance(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSelected) {
            mAdapter.pauseBanner();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSelected) {
            mAdapter.resumeBanner();
        }
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Subscribe
    public void onSelected(NewsFragmentSelectionEvent event) {
        this.mSelected = event.mSelected;
        if (this.mSelected) {
            mAdapter.resumeBanner();
        } else {
            mAdapter.pauseBanner();
        }
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void initView(View view) {
        mAdapter = new NewsAdapter(getActivity());
        mAdapter.setItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.loadNewsCache();
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
    public void setBanner(List<NewsList.NewsEntity> bannerEntityList) {
        mAdapter.setBanner(bannerEntityList);
    }

    @Override
    public void setNewsList(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        mAdapter.updateData(newsEntityList, append);
    }

    @Override
    public void onCacheLoaded() {
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void setRefreshFailed(boolean loadMore) {
        ToastUtil.showToast(getActivity(), R.string.load_fail);
    }

    @Override
    public void hideProgress(boolean loadMore) {
        if (mSwipeRefresh == null) {
            return;
        }
        if (loadMore) {
            mSwipeRefresh.setLoadingMore(false);
        } else {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Subscribe
    public void onBannerItemClick(BannerItemClickEvent event) {
        NewsList.NewsEntity bannerEntity = event.mBannerEntity;
        toDetail(bannerEntity);
    }

    @Override
    public void onItemClick(int position) {
        NewsList.NewsEntity newsEntity = mAdapter.getItem(position);
        toDetail(newsEntity);
    }

    private void toDetail(final NewsList.NewsEntity newsEntity) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.VIDEO_BACKGROUND, newsEntity.getBackground());
        intent.putExtra(NewsDetailActivity.VIDEO_TITLE, newsEntity.getTitle());
        intent.putExtra(NewsDetailActivity.VIDEO_DATE, newsEntity.getTime());
        intent.putExtra(NewsDetailActivity.VIDEO_DESCRIPTION, newsEntity.getDescription());
        intent.putExtra(NewsDetailActivity.NEWS_NID, newsEntity.getNid());
        intent.putExtra(NewsDetailActivity.NEWS_DATE, newsEntity.getDate());
        startActivity(intent);
    }

    @Override
    public void onHeaderClick() {

    }

    @Override
    public void onFooterClick() {

    }
}
