package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.rxbus.OnNextSubscriber;
import com.fangxu.dota2helper.rxbus.RxBus;
import com.fangxu.dota2helper.rxbus.WatchedVideoGetEvent;
import com.fangxu.dota2helper.greendao.GreenWatchedVideo;
import com.fangxu.dota2helper.ui.adapter.FloatWatchedVideoAdapter;
import com.fangxu.dota2helper.util.VideoCacheManager;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/7/19.
 */
public class WatchedVideoListActivity extends BaseVideoListActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mAdapter = new FloatWatchedVideoAdapter(this, new WatchedVideoSelectCountCallback() {
            @Override
            public void onWatchedVideoSelect(int count) {
                mDeleteButton.setCount(count);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration((FloatWatchedVideoAdapter) mAdapter);
        mRecyclerView.addItemDecoration(decoration);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                decoration.invalidateHeaders();
            }
        });

        VideoCacheManager.INSTANCE.getWatchedVideo();

        RxBus.getDefault().toObservable(WatchedVideoGetEvent.class).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnNextSubscriber<WatchedVideoGetEvent>() {
                    @Override
                    public void onNext(WatchedVideoGetEvent watchedVideoGetEvent) {
                        if (watchedVideoGetEvent.mSuccess) {
                            List<GreenWatchedVideo> videos = watchedVideoGetEvent.mGreenWatchedVideos;
                            if (videos.size() > 0) {
                                mAdapter.setData(videos);
                                mEmptyView.setVisibility(View.GONE);
                            } else {
                                showEmptyView();
                            }
                        } else {
                            showEmptyView();
                        }
                    }
                });
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyHint.setText(R.string.watched_no_video_hint);
        mEmptyIcon.setBackgroundResource(R.drawable.ic_watched_no_video_icon);
    }

    @Override
    protected boolean menuEditEnable() {
        return mAdapter.getItemCount() > 0;
    }

    @Override
    protected int getTitleResId() {
        return R.string.watch_video_history;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
