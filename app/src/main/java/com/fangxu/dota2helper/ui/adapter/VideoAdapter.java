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

    private ItemClickListener mItemClickListener = null;

    public interface ItemClickListener{
        void onItemClick(String title, String publishTime, String date, String vid, String background, String ykVid);
    }

    public VideoAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick((String)view.getTag(R.id.tag_title), (String)view.getTag(R.id.tag_publish_in)
                            , (String)view.getTag(R.id.tag_date), (String)view.getTag(R.id.tag_id)
                            , (String)view.getTag(R.id.tag_background), (String)view.getTag(R.id.tag_ykvid));
                }
            }
        });
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoList.VideoEntity entity = mVideoEntityList.get(position);
        holder.itemView.setTag(R.id.tag_title, entity.getTitle());
        holder.itemView.setTag(R.id.tag_publish_in, entity.getPublishin());
        holder.itemView.setTag(R.id.tag_date, entity.getDate());
        holder.itemView.setTag(R.id.tag_id, entity.getVid());
        holder.itemView.setTag(R.id.tag_background, entity.getBackground());
        holder.itemView.setTag(R.id.tag_ykvid, entity.getYkvid());
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
            String videoLength = videoEntity.getVideolength();
            videoLength = "-1".equals(videoLength) ? "--:--" : videoLength;
            mVideoLength.setText("时长:" + videoLength);
            mPublishTime.setText(videoEntity.getPublishin());
        }
    }
}
