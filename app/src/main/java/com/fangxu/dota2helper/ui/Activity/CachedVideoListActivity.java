package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

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
    }

    private void setData() {
        Iterator iter1 = DownloadManager.getInstance().getDownloadedData().entrySet().iterator();
        List<DownloadInfo> downloadInfoList = new ArrayList<>();
        while (iter1.hasNext()) {
            Map.Entry entry = (Map.Entry) iter1.next();
            DownloadInfo downloadInfo = (DownloadInfo) entry.getValue();
            downloadInfoList.add(downloadInfo);
        }

        mDownloadingInfo = new DownloadingInfo();
        Map downloadingMap = DownloadManager.getInstance().getDownloadingData();
        if (downloadingMap.isEmpty()) {
            mDownloadingInfo.setFirstDownloadingInfo(null);
            mDownloadingInfo.setDownloadingCount(0);
        } else {
            boolean firstInfoSetted = false;
            Iterator iter2 = downloadingMap.entrySet().iterator();
            DownloadInfo firstInfo = null;
            while (iter2.hasNext()) {
                Map.Entry entry = (Map.Entry)iter2.next();
                DownloadInfo downloadInfo = (DownloadInfo)entry.getValue();
                if (firstInfo == null) {
                    firstInfo = downloadInfo;
                }
                if (downloadInfo.state == DownloadInfo.STATE_DOWNLOADING) {
                    mDownloadingInfo.setFirstDownloadingInfo(downloadInfo);
                    firstInfoSetted = true;
                    break;
                }
            }
            if (!firstInfoSetted) {
                mDownloadingInfo.setFirstDownloadingInfo(firstInfo);
            }
            mDownloadingInfo.setDownloadingCount(downloadingMap.size());
        }
        ((CachedVideoAdapter) mAdapter).setDownloadingInfo(mDownloadingInfo, false);

        mAdapter.setData(downloadInfoList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DownloadManager.getInstance().setOnChangeListener(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
        DownloadManager.getInstance().setOnChangeListener(this);
    }

    @Override
    protected boolean menuEditEnable() {
        if (mDownloadingInfo.getDownloadingCount() > 0) {
            return mAdapter.getItemCount() > 1;
        }
        return mAdapter.getItemCount() > 0;
    }

    @Override
    public void onChanged(DownloadInfo info) {
        Log.d("DXXXCachEDActivity", "onChanged, progress=" + info.progress);
        if (info.state == DownloadInfo.STATE_DOWNLOADING) {
            mDownloadInfo = info;
            updateDownloadingInfo(false);
        }
    }

    @Override
    public void onFinish() {
        Log.d("DXXXCachEDActivity", "onFinish");
        updateDownloadingInfo(true);
    }

    private void updateDownloadingInfo(final boolean downloaded) {
        mDownloadingInfo.setFirstDownloadingInfo(mDownloadInfo);
        mDownloadingInfo.setDownloadingCount(DownloadManager.getInstance().getDownloadingData().size());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((CachedVideoAdapter) mAdapter).setDownloadingInfo(mDownloadingInfo, downloaded);
            }
        });
    }

    @Override
    protected int getTitleResId() {
        return R.string.cached_video;
    }
}
