package com.fangxu.dota2helper.bean;

import com.youku.service.download.DownloadInfo;

/**
 * Created by dear33 on 2016/7/27.
 */
public class DownloadingInfo {
    private int mDownloadingCount = 0;
    private DownloadInfo mFirstDownloadingInfo = null;

    public DownloadingInfo() {

    }

    public DownloadingInfo(int mDownloadingCount, DownloadInfo mFirstDownloadingInfo) {
        this.mDownloadingCount = mDownloadingCount;
        this.mFirstDownloadingInfo = mFirstDownloadingInfo;
    }

    public int getDownloadingCount() {
        return mDownloadingCount;
    }

    public void setDownloadingCount(int mDownloadingCount) {
        this.mDownloadingCount = mDownloadingCount;
    }

    public DownloadInfo getFirstDownloadingInfo() {
        return mFirstDownloadingInfo;
    }

    public void setFirstDownloadingInfo(DownloadInfo mFirstDownloadingInfo) {
        this.mFirstDownloadingInfo = mFirstDownloadingInfo;
    }
}
