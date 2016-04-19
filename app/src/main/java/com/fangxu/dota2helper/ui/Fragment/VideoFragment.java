package com.fangxu.dota2helper.ui.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.VideoList;
import com.fangxu.dota2helper.presenter.IVideoView;
import com.fangxu.dota2helper.presenter.VideoPresenter;
import com.fangxu.dota2helper.ui.adapter.VideoAdapter;
import com.fangxu.dota2helper.util.NavUtil;
import com.fangxu.dota2helper.util.ToastUtil;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoFragment extends RefreshBaseFragment implements IVideoView{
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private VideoPresenter mPresenter;
    private VideoAdapter mAdapter;

    @Override
    public void init() {
        int position = FragmentPagerItem.getPosition(getArguments());
        String videoType = NavUtil.videoTypeList[position];
        mPresenter = new VideoPresenter(this, videoType);
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_swipe_to_load;
    }

    @Override
    public void initView(View view) {
        mAdapter = new VideoAdapter(getActivity());
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
    public void onLoadMore() {
        mPresenter.doLoadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.doRefresh();
    }

    @Override
    public void setVideoList(List<VideoList.VideoEntity> videoEntityList, boolean append) {
        if (append) {
            mSwipeRefresh.setLoadingMore(false);
        } else {
            mSwipeRefresh.setRefreshing(false);
        }
        mAdapter.updateData(videoEntityList, append);
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
}
