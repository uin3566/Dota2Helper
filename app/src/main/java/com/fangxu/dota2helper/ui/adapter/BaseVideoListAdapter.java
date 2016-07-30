package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;

import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dear33 on 2016/7/27.
 */
public abstract class BaseVideoListAdapter<T> extends CommonRecyclerAdapter<T> {
    protected WatchedVideoSelectCountCallback mCountCallback;
    protected Set<String> mSelectedVideos;
    protected boolean mIsEditState = false;
    protected DecimalFormat mDecimalFormat;

    public abstract int deleteSelected();

    public abstract void selectAll();

    public abstract String getYkVid(int position);

    public BaseVideoListAdapter(Context context) {
        super(context);
    }

    public BaseVideoListAdapter(Context context, WatchedVideoSelectCountCallback countCallback) {
        super(context);
        mSelectedVideos = new HashSet<>();
        mCountCallback = countCallback;
        mDecimalFormat = new DecimalFormat("0.0");
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    protected String getVideoSize(long videoSizeByte) {
        String size = mDecimalFormat.format((double) videoSizeByte / (1024 * 1024));
        return size + "MB";
    }

    public void updateState(boolean isEditState) {
        mIsEditState = isEditState;
        mNeedIntervalController.mItemIntervalSwitchOn = !mIsEditState;
        if (!mIsEditState) {
            mSelectedVideos.clear();
            notifyVideoSelectCount();
        }
        notifyDataSetChanged();
    }

    @Override
    protected void onClickItem(int position) {
        super.onClickItem(position);
        String ykVid = getYkVid(position);
        if (mIsEditState) {
            if (mSelectedVideos.contains(ykVid)) {
                mSelectedVideos.remove(ykVid);
            } else {
                mSelectedVideos.add(ykVid);
            }
            notifyDataSetChanged();
            notifyVideoSelectCount();
        }
    }

    protected void notifyVideoSelectCount() {
        if (mCountCallback != null) {
            mCountCallback.onWatchedVideoSelect(mSelectedVideos.size());
        }
    }
}
