package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;

import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

import java.util.Iterator;

/**
 * Created by dear33 on 2016/7/27.
 */
public abstract class BaseCacheVideoAdapter extends BaseVideoListAdapter<DownloadInfo> {

    protected abstract void deleteCache(DownloadInfo downloadInfo);

    public BaseCacheVideoAdapter(Context context) {
        super(context);
    }

    public BaseCacheVideoAdapter(Context context, WatchedVideoSelectCountCallback countCallback) {
        super(context, countCallback);
    }

    @Override
    public void updateState(boolean isEditState) {
        mIsEditState = isEditState;
        mNeedIntervalController.mItemIntervalSwitchOn = !mIsEditState;
        notifyDataSetChanged();
    }

    @Override
    public int deleteSelected() {
        Iterator<DownloadInfo> iterator = mData.iterator();
        while (iterator.hasNext()) {
            DownloadInfo video = iterator.next();
            String key = video.videoid;
            if (mSelectedVideos.contains(key)) {
                iterator.remove();
                deleteCache(video);
            }
        }
        int deleteCount = mSelectedVideos.size();
        mSelectedVideos.clear();
        notifyDataSetChanged();
        notifyVideoSelectCount();
        return deleteCount;
    }

    @Override
    public void selectAll() {
        if (mSelectedVideos.isEmpty()) {
            for (DownloadInfo video : mData) {
                mSelectedVideos.add(video.videoid);
            }
        } else {
            if (mSelectedVideos.size() == mData.size()) {
                mSelectedVideos.clear();
            } else {
                for (DownloadInfo video : mData) {
                    mSelectedVideos.add(video.videoid);
                }
            }
        }
        notifyDataSetChanged();
        notifyVideoSelectCount();
    }

    @Override
    public String getYkVid(int position) {
        return getItem(position).videoid;
    }
}
