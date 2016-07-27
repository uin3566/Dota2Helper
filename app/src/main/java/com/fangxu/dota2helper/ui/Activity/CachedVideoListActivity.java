package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.DownloadingInfo;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.ui.adapter.CachedVideoAdapter;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;
import com.youku.service.download.OnChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/25.
 */
public class CachedVideoListActivity extends BaseVideoListActivity implements OnChangeListener {
    private DownloadingInfo mDownloadingInfo;
    private DownloadInfo mDownloadInfo;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mAdapter = new CachedVideoAdapter(this, new WatchedVideoSelectCountCallback() {
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

    private void setData() {
        Iterator iterator = DownloadManager.getInstance().getDownloadedData().entrySet().iterator();
        List<DownloadInfo> downloadInfoList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            DownloadInfo downloadInfo = (DownloadInfo) entry.getValue();
            downloadInfoList.add(downloadInfo);
        }

        Map<String, DownloadInfo> downloadingInfoMap = DownloadManager.getInstance().getDownloadingData();
        int size = downloadingInfoMap.size();
        mDownloadingInfo = new DownloadingInfo();
        mDownloadingInfo.setDownloadingCount(downloadingInfoMap.size());
        if (size == 0) {
            mDownloadingInfo.setFirstDownloadingInfo(null);
        } else {
            iterator = downloadingInfoMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                DownloadInfo downloadInfo = (DownloadInfo) entry.getValue();
                if (downloadInfo.state == DownloadInfo.STATE_DOWNLOADING
                        || downloadInfo.state == DownloadInfo.STATE_PAUSE) {
                    mDownloadingInfo.setFirstDownloadingInfo(downloadInfo);
                    break;
                }
            }
            if (mDownloadingInfo.getFirstDownloadingInfo() == null) {
                iterator = downloadingInfoMap.entrySet().iterator();
                Map.Entry entry = (Map.Entry) iterator.next();
                DownloadInfo downloadInfo = (DownloadInfo) entry.getValue();
                mDownloadingInfo.setFirstDownloadingInfo(downloadInfo);
            }
        }

        DownloadManager.getInstance().setOnChangeListener(this);
        ((CachedVideoAdapter) mAdapter).setDownloadingInfo(mDownloadingInfo);
        mAdapter.setData(downloadInfoList);
    }

    @Override
    public void onChanged(DownloadInfo info) {
        mDownloadInfo = info;
        updateDownloadingInfo();
    }

    @Override
    public void onFinish() {
        updateDownloadingInfo();
    }

    private void updateDownloadingInfo() {
        Map<String, DownloadInfo> downloadingInfoMap = DownloadManager.getInstance().getDownloadingData();
        mDownloadingInfo.setFirstDownloadingInfo(mDownloadInfo);
        mDownloadingInfo.setDownloadingCount(downloadingInfoMap.size());
        ((CachedVideoAdapter) mAdapter).setDownloadingInfo(mDownloadingInfo);
    }

    @Override
    protected void onDestroy() {
        DownloadManager.getInstance().setOnChangeListener(null);
        super.onDestroy();
    }

    @Override
    protected int getTitleResId() {
        return R.string.cached_video;
    }
}
