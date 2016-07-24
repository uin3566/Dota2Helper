package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.eventbus.BusProvider;
import com.fangxu.dota2helper.eventbus.WatchedVideoGetEvent;
import com.fangxu.dota2helper.greendao.GreenWatchedVideo;
import com.fangxu.dota2helper.ui.adapter.CommonRecyclerAdapter;
import com.fangxu.dota2helper.ui.adapter.FloatWatchedVideoAdapter;
import com.fangxu.dota2helper.ui.widget.CountButton;
import com.fangxu.dota2helper.util.VideoCacheManager;
import com.squareup.otto.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/19.
 */
public class WatchedVideoActivity extends BaseActivity {
    @Bind(R.id.recycler_watched_videos)
    RecyclerView mRecyclerView;
    @Bind(R.id.fl_empty_view)
    FrameLayout mEmptyView;
    @Bind(R.id.ll_select_delete_controllers)
    LinearLayout mSelectDeleteControllers;
    @Bind(R.id.cb_delete)
    CountButton mDeleteButton;

    private FloatWatchedVideoAdapter mAdapter;
    private boolean mIsEditState = false;

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
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_edit) {
                    mIsEditState = !mIsEditState;
                    updateMenuTitle(item);
                    mAdapter.updateState(mIsEditState);
                    mSelectDeleteControllers.setVisibility(mIsEditState ? View.VISIBLE : View.GONE);
                }
                return true;
            }
        });

        mAdapter = new FloatWatchedVideoAdapter(this, new WatchedVideoSelectCountCallback() {
            @Override
            public void onWatchedVideoSelect(int count) {
                mDeleteButton.setCount(count);
            }
        });
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

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        VideoCacheManager.INSTANCE.getWatchedVideo();
    }

    private void updateMenuTitle(MenuItem item) {
        if (mIsEditState) {
            item.setTitle(R.string.cancel);
        } else {
            item.setTitle(R.string.edit);
        }
    }

    @OnClick(R.id.tv_select_all)
    public void clickSelectAll(View selectAll) {
        mAdapter.selectAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_watched_video, menu);
        return true;
    }

    @Subscribe
    public void onGetWatchedVideo(WatchedVideoGetEvent event) {
        if (event.mSuccess) {
            List<GreenWatchedVideo> videos = event.mGreenWatchedVideos;
            if (videos.size() > 0) {
                mAdapter.setData(videos);
                mEmptyView.setVisibility(View.GONE);
            } else {
                mEmptyView.setVisibility(View.VISIBLE);
            }
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
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
