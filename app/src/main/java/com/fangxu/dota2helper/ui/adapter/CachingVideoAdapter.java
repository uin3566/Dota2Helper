package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.ui.widget.TickButton;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

import butterknife.Bind;

/**
 * Created by dear33 on 2016/7/27.
 */
public class CachingVideoAdapter extends BaseCacheVideoAdapter {
    private Handler handler = new Handler(Looper.getMainLooper());
    private int mCurrentCachingPos = -1;

    public CachingVideoAdapter(Context context) {
        this(context, null);
    }

    public CachingVideoAdapter(Context context, WatchedVideoSelectCountCallback callback) {
        super(context, callback);
    }

    public void updateDownloadingView(DownloadInfo downloadInfo) {
        if (mCurrentCachingPos != -1) {
            notifyUi(downloadInfo, true);
        } else {
            for (int i = 0; i < getItemCount(); i++) {
                DownloadInfo info = getItem(i);
                if (info.videoid.equals(downloadInfo.videoid)) {
                    mCurrentCachingPos = i;
                    notifyUi(downloadInfo, true);
                    break;
                }
            }
        }
    }

    private void notifyUi(DownloadInfo downloadInfo, boolean update) {
        if (update) {
            mData.set(mCurrentCachingPos, downloadInfo);
        } else {
            mData.remove(downloadInfo);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void deleteDownloadedView(DownloadInfo downloadInfo) {
        notifyUi(downloadInfo, false);
        mCurrentCachingPos = -1;
    }

    @Override
    protected void deleteCache(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().deleteDownloading(downloadInfo.taskId);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_caching_video, parent, false);
        return new CachingVideoViewHolder(view);
    }

    public class CachingVideoViewHolder extends CommonViewHolder {
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tb_select)
        TickButton mTickButton;
        @Bind(R.id.tv_video_size)
        TextView mVideoSize;
        @Bind(R.id.progressbar)
        ProgressBar mProgressBar;
        @Bind(R.id.tv_cached_size)
        TextView mCachedSize;
        @Bind(R.id.tv_cache_state)
        TextView mCacheState;

        public CachingVideoViewHolder(View itemView) {
            super(itemView);
            mProgressBar.setMax(100);
        }

        @Override
        public void fillView(int position) {
            DownloadInfo info = getItem(position);
            Glide.with(mContext).load(info.imgUrl).placeholder(R.drawable.img_background_default).into(mBackground);
            mTitle.setText(info.title);
            mVideoSize.setText(getVideoSize(info.size));
            mCachedSize.setText(getVideoSize(info.downloadedSize));

            if (info.state == DownloadInfo.STATE_DOWNLOADING) {
                mCacheState.setText("正在下载");
            } else if (info.state == DownloadInfo.STATE_PAUSE) {
                mCacheState.setText("暂停中");
            } else if (info.state == DownloadInfo.STATE_INIT || info.state == DownloadInfo.STATE_EXCEPTION
                    || info.state == DownloadInfo.STATE_WAITING) {
                mCacheState.setText("等待中");
            }

            mProgressBar.setProgress((int) info.progress);

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
