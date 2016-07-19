package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.eventbus.BusProvider;
import com.fangxu.dota2helper.eventbus.WatchedVideoGetEvent;
import com.fangxu.dota2helper.greendao.GreenWatchedVideo;
import com.fangxu.dota2helper.ui.adapter.FloatWatchedVideoAdapter;
import com.fangxu.dota2helper.util.VideoCacheManager;
import com.squareup.otto.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/19.
 */
public class WatchedVideoActivity extends BaseActivity {
    @Bind(R.id.recycler_watched_videos)
    RecyclerView mRecyclerView;

    private FloatWatchedVideoAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_watched_video;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        BusProvider.getInstance().register(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mAdapter = new FloatWatchedVideoAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mAdapter);
        mRecyclerView.addItemDecoration(decoration);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                decoration.invalidateHeaders();
            }
        });

        VideoCacheManager.INSTANCE.getWatchedVideo();
    }

    @Subscribe
    public void onGetWatchedVideo(WatchedVideoGetEvent event) {
        if (event.mSuccess) {
            List<GreenWatchedVideo> videos = event.mGreenWatchedVideos;
            if (videos.size() > 0) {
                mAdapter.setData(videos);
            } else {

            }
        } else {

        }
    }

    @Override
    protected boolean applySystemBarDrawable() {
        return true;
    }

    @Override
    protected int getTitleResId() {
        return R.string.watch_video_history;
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }
}
