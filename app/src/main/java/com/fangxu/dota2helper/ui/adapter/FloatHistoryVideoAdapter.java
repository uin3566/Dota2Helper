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
import com.fangxu.dota2helper.greendao.GreenWatchedVideo;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/19.
 */
public class FloatHistoryVideoAdapter extends CommonRecyclerAdapter<GreenWatchedVideo> implements
        StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>{
    public FloatHistoryVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watched_video, parent, false);
        return new VideoItemHolder(view);
    }

    @Override
    public long getHeaderId(int i) {
        if (i == 0) {
            return -1;
        } else {
            return getItem(i).getVideoyoukuvid().charAt(0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_float_view, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TextView textView = (TextView)viewHolder.itemView;
        textView.setText(String.valueOf(getItem(i).getVideoyoukuvid().charAt(0)));
    }

    private class VideoItemHolder extends CommonViewHolder {
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_watched_percent)
        TextView mWatchedPercent;

        public VideoItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            GreenWatchedVideo greenWatchedVideo = getItem(position);
            mTitle.setText(greenWatchedVideo.getVideotitle());
            Glide.with(mContext).load(greenWatchedVideo.getVideobackground()).placeholder(R.drawable.img_background_default).into(mBackground);
            mWatchedPercent.setText(getWatchedPercentText(greenWatchedVideo));
        }

        private String getWatchedPercentText(GreenWatchedVideo greenWatchedVideo) {
            boolean isVideoEnded = greenWatchedVideo.getVideoEnded();
            String ret;
            if (isVideoEnded) {
                ret = "已看完";
            } else {
                int percent = (int)((float) greenWatchedVideo.getVideoplaytime() / (float) greenWatchedVideo.getVideoduration() * 100);
                ret = "已观看至" + percent + "%";
            }
            return ret;
        }
    }
}
