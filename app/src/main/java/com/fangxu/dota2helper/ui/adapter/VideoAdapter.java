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
import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.bean.VideoList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<VideoList.VideoEntity> mVideoEntityList = new ArrayList<>();

    public VideoAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void updateData(List<VideoList.VideoEntity> videoEntityList, boolean append) {
        if (!append) {
            mVideoEntityList.clear();
        }
        mVideoEntityList.addAll(videoEntityList);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoList.VideoEntity entity = mVideoEntityList.get(position);
        holder.fillView(entity);
    }

    @Override
    public int getItemCount() {
        return mVideoEntityList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
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
            ButterKnife.bind(this, itemView);
        }

        public void fillView(VideoList.VideoEntity videoEntity) {
            Glide.with(mContext).load(videoEntity.getBackground()).placeholder(R.drawable.image_background_default).into(mBackground);
            mTitle.setText(videoEntity.getTitle());
            mVideoLength.setText("时长:" + videoEntity.getVideolength());
            mPublishTime.setText(videoEntity.getPublishin());
        }
    }
}
