package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.ui.adapter.CachingVideoAdapter;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;
import com.youku.service.download.OnChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26.
 */
public class CachingVideoListActivity extends BaseVideoListActivity implements OnChangeListener, CachingVideoAdapter.HideEditCallback {
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mAdapter = new CachingVideoAdapter(this, new WatchedVideoSelectCountCallback() {
            @Override
            public void onWatchedVideoSelect(int count) {
                mDeleteButton.setCount(count);
            }
        });
        ((CachingVideoAdapter) mAdapter).setHideEditCallback(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setData() {
        int pauseCount = 0;
        Iterator iterator = DownloadManager.getInstance().getDownloadingData().entrySet().iterator();
        List<DownloadInfo> downloadingInfoList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            DownloadInfo downloadInfo = (DownloadInfo) entry.getValue();
            if (downloadInfo.state == DownloadInfo.STATE_PAUSE) {
                pauseCount++;
            }
            downloadingInfoList.add(downloadInfo);
        }

        ((CachingVideoAdapter) mAdapter).setPauseCount(pauseCount);
        mAdapter.setData(downloadingInfoList);
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyIcon.setBackgroundResource(R.drawable.ic_cached_no_video_icon);
        mEmptyHint.setText(R.string.caching_no_video_hint);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DownloadManager.getInstance().setOnChangeListener(this);
        setData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DownloadManager.getInstance().setOnChangeListener(null);
    }

    @Override
    protected boolean menuEditEnable() {
        return mAdapter.getItemCount() > 0;
    }

    @Override
    public void onEditShouldHide() {
        mSelectDeleteControllers.setVisibility(View.GONE);
        mIsEditState = false;
        showEmptyView();
        updateMenuTitle(mToolbar.getMenu().getItem(0));
    }

    @Override
    public void onChanged(final DownloadInfo info) {
        if (info.state == DownloadInfo.STATE_DOWNLOADING && !mIsEditState) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((CachingVideoAdapter) mAdapter).updateDownloadingView(info);
                }
            });
        }
    }

    @Override
    public void onFinish() {
                runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((CachingVideoAdapter) mAdapter).deleteDownloadedView();
            }
        });
    }

    @Override
    protected int getTitleResId() {
        return R.string.caching_video;
    }
}
