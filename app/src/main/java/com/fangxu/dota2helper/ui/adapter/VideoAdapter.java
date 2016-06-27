package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.VideoList;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoAdapter extends CommonRecyclerAdapter<VideoList.VideoEntity>{
    public VideoAdapter(Context context) {
        super(context);
    }

    public void updateData(List<VideoList.VideoEntity> videoEntityList, boolean append) {
        if (!append) {
            mData.clear();
        }
        mData.addAll(videoEntityList);
        notifyDataSetChanged();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public class VideoViewHolder extends CommonViewHolder{
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_video_length)
        TextView mVideoLength;
        @Bind(R.id.tv_publish_time)
        TextView mPublishTime;

        public VideoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            VideoList.VideoEntity videoEntity = getItem(position);
            Glide.with(mContext).load(videoEntity.getBackground()).placeholder(R.drawable.img_background_default).into(mBackground);
            mTitle.setText(videoEntity.getTitle());
            String videoLength = videoEntity.getVideolength();
            videoLength = "-1".equals(videoLength) ? "--:--" : videoLength;
            mVideoLength.setText("时长:" + videoLength);
            mPublishTime.setText(videoEntity.getPublishin());
        }
    }
}
