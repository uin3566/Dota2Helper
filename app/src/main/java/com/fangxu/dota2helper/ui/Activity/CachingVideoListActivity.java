package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.eventbus.BusProvider;
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
public class CachingVideoListActivity extends BaseVideoListActivity implements OnChangeListener {
    private DownloadInfo mCurrentDownloadInfo;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mAdapter = new CachingVideoAdapter(this, new WatchedVideoSelectCountCallback() {
            @Override
            public void onWatchedVideoSelect(int count) {
                mDeleteButton.setCount(count);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        setData();
    }

    @Override
    protected void onDestroy() {
        DownloadManager.getInstance().setOnChangeListener(null);
        super.onDestroy();
    }

    private void setData() {
        Iterator iterator = DownloadManager.getInstance().getDownloadingData().entrySet().iterator();
        List<DownloadInfo> downloadingInfoList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            DownloadInfo downloadInfo = (DownloadInfo) entry.getValue();
            downloadingInfoList.add(downloadInfo);
        }

        mAdapter.setData(downloadingInfoList);
        DownloadManager.getInstance().setOnChangeListener(this);
    }

    @Override
    public void onChanged(DownloadInfo info) {
        Log.d("DownloadManagerXXX", "onChanged, progress=" + info.progress);
        mCurrentDownloadInfo = info;
        ((CachingVideoAdapter) mAdapter).updateDownloadingView(info);
    }

    @Override
    public void onFinish() {
        Log.d("DownloadManagerXXX", "onFinish");
        ((CachingVideoAdapter) mAdapter).deleteDownloadedView(mCurrentDownloadInfo);
    }

    @Override
    protected int getTitleResId() {
        return R.string.caching_video;
    }
}
