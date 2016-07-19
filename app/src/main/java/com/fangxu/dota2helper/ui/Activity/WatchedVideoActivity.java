package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.eventbus.WatchedVideoGetEvent;
import com.fangxu.dota2helper.ui.adapter.FloatHistoryVideoAdapter;
import com.fangxu.dota2helper.util.VideoCacheManager;
import com.squareup.otto.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/19.
 */
public class WatchedVideoActivity extends BaseActivity{
    @Bind(R.id.recycler_watched_videos)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_watched_video;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FloatHistoryVideoAdapter adapter = new FloatHistoryVideoAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(adapter);
        mRecyclerView.addItemDecoration(decoration);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                decoration.invalidateHeaders();
            }
        });

        VideoCacheManager.INSTANCE.getWatchedVideo();
    }

    @Subscribe
    public void onGetWatchedVideo(WatchedVideoGetEvent event) {

    }

    @Override
    protected boolean applySystemBarDrawable() {
        return true;
    }

    @Override
    protected int getTitleResId() {
        return R.string.watch_video_history;
    }
}
