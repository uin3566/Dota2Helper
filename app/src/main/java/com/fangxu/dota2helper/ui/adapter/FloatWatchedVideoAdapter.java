package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.callback.WatchedVideoSelectCountCallback;
import com.fangxu.dota2helper.greendao.GreenWatchedVideo;
import com.fangxu.dota2helper.ui.Activity.VideoPlayerActivity;
import com.fangxu.dota2helper.ui.widget.TickButton;
import com.fangxu.dota2helper.util.DateUtil;
import com.fangxu.dota2helper.util.VideoCacheManager;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/19.
 */
public class FloatWatchedVideoAdapter extends BaseVideoListAdapter<GreenWatchedVideo> implements
        StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private DateUtil mDateUtil;

    public FloatWatchedVideoAdapter(Context context) {
        this(context, null);
    }

    public FloatWatchedVideoAdapter(Context context, WatchedVideoSelectCountCallback callback) {
        super(context, callback);
        mDateUtil = new DateUtil();
    }

    @Override
    public int deleteSelected() {
        Set<String> selectedVideosCopy = new HashSet<>(mSelectedVideos);
        VideoCacheManager.INSTANCE.deleteSelectedVideo(selectedVideosCopy);
        Iterator<GreenWatchedVideo> iterator = mData.iterator();
        while (iterator.hasNext()) {
            GreenWatchedVideo video = iterator.next();
            String key = video.getVideoyoukuvid();
            if (selectedVideosCopy.contains(key)) {
                iterator.remove();
            }
        }
        int deleteCount = mSelectedVideos.size();
        mSelectedVideos.clear();
        notifyDataSetChanged();
        notifyVideoSelectCount();
        return deleteCount;
    }

    @Override
    public String getYkVid(int position) {
        return getItem(position).getVideoyoukuvid();
    }

    @Override
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
    public void selectAll() {
        if (mSelectedVideos.isEmpty()) {
            for (GreenWatchedVideo video : mData) {
                mSelectedVideos.add(video.getVideoyoukuvid());
            }
        } else {
            if (mSelectedVideos.size() == mData.size()) {
                mSelectedVideos.clear();
            } else {
                for (GreenWatchedVideo video : mData) {
                    mSelectedVideos.add(video.getVideoyoukuvid());
                }
            }
        }
        notifyDataSetChanged();
        notifyVideoSelectCount();
    }

    @Override
    protected void onClickItem(int position) {
        super.onClickItem(position);
        GreenWatchedVideo video = getItem(position);
        if (mIsEditState) {
            String ykvid = video.getVideoyoukuvid();
            if (mSelectedVideos.contains(ykvid)) {
                mSelectedVideos.remove(ykvid);
            } else {
                mSelectedVideos.add(ykvid);
            }
            notifyDataSetChanged();
            notifyVideoSelectCount();
        } else {
            VideoPlayerActivity.toVideoPlayerActivity(mContext, video.getVideotitle(), null, null
                    , video.getVideobackground(), video.getVideoyoukuvid());
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_watched_video, parent, false);
        return new VideoItemHolder(view);
    }

    @Override
    public long getHeaderId(int i) {
        GreenWatchedVideo video = getItem(i);
        long watchedTime = video.getVideowatchtime();
        if (mDateUtil.isToday(watchedTime)) {
            return 1;
        } else if (mDateUtil.isInSevenDays(watchedTime)) {
            return 2;
        } else if (mDateUtil.isOutSevenDays(watchedTime)) {
            return 3;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_float_view, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TextView textView = (TextView) viewHolder.itemView;
        long watchedTime = getItem(i).getVideowatchtime();
        textView.setText(mDateUtil.getTimeText(watchedTime));
        textView.setCompoundDrawablesWithIntrinsicBounds(getDrawable(watchedTime), 0, 0, 0);
        textView.setCompoundDrawablePadding(mContext.getResources().getDimensionPixelSize(R.dimen.base_little_margin));
    }

    private int getDrawable(long watchedTime) {
        if (mDateUtil.isToday(watchedTime)) {
            return R.drawable.small_circle_blue;
        } else if (mDateUtil.isInSevenDays(watchedTime)) {
            return R.drawable.small_circle_red;
        } else if (mDateUtil.isOutSevenDays(watchedTime)) {
            return R.drawable.small_circle_orange;
        } else {
            return R.drawable.small_circle_orange;
        }
    }

    public class VideoItemHolder extends CommonViewHolder {
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_watched_percent)
        TextView mWatchedPercent;
        @Bind(R.id.tb_select)
        TickButton mTickButton;

        public VideoItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            GreenWatchedVideo greenWatchedVideo = getItem(position);
            mTitle.setText(greenWatchedVideo.getVideotitle());
            Glide.with(mContext).load(greenWatchedVideo.getVideobackground()).placeholder(R.drawable.img_background_default).into(mBackground);
            mWatchedPercent.setText(getWatchedPercentText(greenWatchedVideo));
            if (mIsEditState) {
                mTickButton.setVisibility(View.VISIBLE);
                String ykvid = greenWatchedVideo.getVideoyoukuvid();
                if (mSelectedVideos.contains(ykvid)) {
                    mTickButton.setSelected(true);
                } else {
                    mTickButton.setSelected(false);
                }
            } else {
                mTickButton.setVisibility(View.GONE);
            }
        }

        private String getWatchedPercentText(GreenWatchedVideo greenWatchedVideo) {
            boolean isVideoEnded = greenWatchedVideo.getVideoEnded();
            String ret;
            if (isVideoEnded) {
                ret = "已看完";
            } else {
                int percent = (int) ((float) greenWatchedVideo.getVideoplaytime() / (float) greenWatchedVideo.getVideoduration() * 100);
                ret = "已观看至" + percent + "%";
            }
            return ret;
        }
    }
}
