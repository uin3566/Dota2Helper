package com.fangxu.dota2helper.ui.adapter;

import android.app.Activity;
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
import com.fangxu.dota2helper.ui.Activity.FullScreenVideoActivity;
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
    private static final int ITEM_CACHED_COUNT = 104;

    public CachedVideoAdapter(Context context) {
        this(context, null);
    }

    public CachedVideoAdapter(Context context, WatchedVideoSelectCountCallback callback) {
        super(context, callback);
        mDownloadingInfo = new DownloadingInfo();
    }

    public void setDownloadingInfo(DownloadingInfo downloadingInfo, boolean downloaded) {
        if (downloaded) {
            mData.add(downloadingInfo.getFirstDownloadingInfo());
        }

        setHasHeader(downloadingInfo.getDownloadingCount() > 0);
        if (downloadingInfo.getDownloadingCount() > 0) {
            if (mDownloadingInfo.getDownloadingCount() == 0) {
                mDownloadingInfo = downloadingInfo;
                notifyItemInserted(0);
            } else {
                mDownloadingInfo = downloadingInfo;
                notifyItemChanged(0);
            }
        } else {
            if (mDownloadingInfo.getDownloadingCount() > 0) {
                mDownloadingInfo = downloadingInfo;
                notifyItemRemoved(0);
            } else {
                mDownloadingInfo = downloadingInfo;
                notifyDataSetChanged();
            }
        }

        if (downloaded) {
            notifyItemInserted(getItemCount());
            notifyItemChanged(getCachedCountPosition());
        }
    }

    private int getCachedCountPosition() {
        return isHasHeader() ? 1 : 0;
    }

    @Override
    public String getYkVid(int position) {
        return getItem(position).videoid;
    }

    @Override
    public int getItemViewType(int position) {
        int pos = getCachedCountPosition();
        if (position == pos && !mData.isEmpty()) {
            return ITEM_CACHED_COUNT;
        }
        return super.getItemViewType(position);
    }

    @Override
    public DownloadInfo getItem(int position) {
        return super.getItem(position - 1);
    }

    @Override
    public int getItemCount() {
        if (mData.isEmpty()) {
            return super.getItemCount();
        }
        return super.getItemCount() + 1;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        CommonViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == ITEM_HEADER) {
            view = inflater.inflate(R.layout.item_header_caching_videos, parent, false);
            viewHolder = new HeaderViewHolder(view);
        } else if (viewType == ITEM_NORMAL) {
            view = inflater.inflate(R.layout.item_cached_video, parent, false);
            viewHolder = new CachedVideoViewHolder(view);
        } else if (viewType == ITEM_CACHED_COUNT) {
            view = inflater.inflate(R.layout.item_cached_video_count, parent, false);
            viewHolder = new CachedVideoCountViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    protected void deleteCache(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().deleteDownloaded(downloadInfo);
    }

    @Override
    protected void onClickHeader() {
        super.onClickHeader();
        if (!mIsEditState) {
            Intent intent = new Intent(mContext, CachingVideoListActivity.class);
            mContext.startActivity(intent);
        }
    }

    @Override
    protected void onClickItem(int position) {
        if (!mData.isEmpty() && position == getCachedCountPosition()) {
            return;
        }
        super.onClickItem(position);
        if (!mIsEditState) {
            DownloadInfo info = getItem(position);
            FullScreenVideoActivity.startFullScreenVideoActivity((Activity) mContext, info.videoid, info.imgUrl);
        }
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
            mTitle.setText(info.title.trim());
            mDownloadingCount.setCount(mDownloadingInfo.getDownloadingCount());
            mProgressBar.setProgress((int) info.progress);
            mCachedSize.setText(getVideoSize(info.downloadedSize));
            mVideoSize.setText(getVideoSize(info.size));
        }
    }

    public class CachedVideoCountViewHolder extends CommonViewHolder {
        @Bind(R.id.cb_downloaded_count)
        CountButton mDownloadedCount;

        public CachedVideoCountViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            mDownloadedCount.setCount(mData.size());
        }
    }

    public class CachedVideoViewHolder extends CommonViewHolder {
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
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
    }
}
