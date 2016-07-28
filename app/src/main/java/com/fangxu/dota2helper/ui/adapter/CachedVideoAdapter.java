package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.DownloadingInfo;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.ui.Activity.CachingVideoListActivity;
import com.fangxu.dota2helper.ui.widget.CountButton;
import com.fangxu.dota2helper.ui.widget.TickButton;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

import java.util.Iterator;

import butterknife.Bind;

/**
 * Created by dear33 on 2016/7/27.
 */
public class CachedVideoAdapter extends BaseCacheVideoAdapter {
    private DownloadingInfo mDownloadingInfo;

    public CachedVideoAdapter(Context context) {
        this(context, null);
    }

    public CachedVideoAdapter(Context context, WatchedVideoSelectCountCallback callback) {
        super(context, callback);
    }

    public void setDownloadingInfo(DownloadingInfo downloadingInfo, boolean downloaded) {
        if (downloaded) {
            mData.add(downloadingInfo.getFirstDownloadingInfo());
        }

        mDownloadingInfo = downloadingInfo;
        setHasHeader(mDownloadingInfo.getDownloadingCount() > 0);
        notifyDataSetChanged();
    }

    @Override
    public String getYkVid(int position) {
        return getItem(position).videoid;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        CommonViewHolder viewHolder = null;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_header_caching_videos, parent, false);
            viewHolder = new HeaderViewHolder(view);
        } else if (viewType == ITEM_NORMAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_cached_video, parent, false);
            viewHolder = new CachedVideoViewHolder(view);
        }
        return viewHolder;
    }

    public class HeaderViewHolder extends CommonViewHolder {
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.cb_downloading_count)
        CountButton mDownloadingCount;
        @Bind(R.id.progressbar)
        ProgressBar mProgressBar;
        @Bind(R.id.tv_cached_size)
        TextView mCachedSize;
        @Bind(R.id.tv_video_size)
        TextView mVideoSize;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            DownloadInfo info = mDownloadingInfo.getFirstDownloadingInfo();
            mTitle.setText(info.title);
            mDownloadingCount.setCount(mDownloadingInfo.getDownloadingCount());
            mProgressBar.setProgress((int) info.progress);
            mCachedSize.setText(getVideoSize(info.downloadedSize));
            mVideoSize.setText(getVideoSize(info.size));
        }
    }

    @Override
    protected void deleteCache(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().deleteDownloaded(downloadInfo);
    }

    @Override
    protected void onClickHeader() {
        super.onClickHeader();
        Intent intent = new Intent(mContext, CachingVideoListActivity.class);
        mContext.startActivity(intent);
    }

    public class CachedVideoViewHolder extends CommonViewHolder {
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_watched_percent)
        TextView mWatchedPercent;
        @Bind(R.id.tb_select)
        TickButton mTickButton;
        @Bind(R.id.tv_video_size)
        TextView mVideoSize;

        public CachedVideoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            DownloadInfo info = getItem(position);
            Glide.with(mContext).load(info.imgUrl).placeholder(R.drawable.img_background_default).into(mBackground);
            mTitle.setText(info.title);
            mVideoSize.setText(getVideoSize(info.size));
            mWatchedPercent.setText(getWatchedPercentText(info));
            if (mIsEditState) {
                mTickButton.setVisibility(View.VISIBLE);
                if (mSelectedVideos.contains(info.videoid)) {
                    mTickButton.setSelected(true);
                } else {
                    mTickButton.setSelected(false);
                }
            } else {
                mTickButton.setVisibility(View.GONE);
            }
        }

        private String getWatchedPercentText(DownloadInfo info) {
            float watchedSecond = info.playTime;
            float totalSecond = info.seconds;
            float ratio = watchedSecond / totalSecond;
            String ret;
            if (ratio == 1) {
                ret = "已看完";
            } else {
                int percent = (int) (ratio * 100);
                ret = "已观看至" + percent + "%";
            }
            return ret;
        }
    }
}
