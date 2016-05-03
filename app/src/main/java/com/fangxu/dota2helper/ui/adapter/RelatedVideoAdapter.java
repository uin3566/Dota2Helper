package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.RelatedVideoList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenov0 on 2016/4/30.
 */
public class RelatedVideoAdapter extends BaseAdapter {
    private List<RelatedVideoList.RelatedVideoEntity> mVideoList = new ArrayList<>();
    private Context mContext;
    private RelatedVideoClickListener mItemClickListener;

    public RelatedVideoAdapter(Context context, RelatedVideoClickListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    public void setData(List<RelatedVideoList.RelatedVideoEntity> videoList) {
        mVideoList = videoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mVideoList.size();
    }

    @Override
    public Object getItem(int i) {
        return mVideoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_related_video, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final RelatedVideoList.RelatedVideoEntity entity = (RelatedVideoList.RelatedVideoEntity)getItem(i);
        holder.mTitle.setText(entity.getTitle());
        holder.mVideoLength.setText("时长:" + transformDuration((int)entity.getDuration()));
        holder.mPlayTimes.setText(entity.getView_count() + "次播放");
        Glide.with(mContext).load(entity.getThumbnail()).placeholder(R.drawable.image_background_default).into(holder.mBackground);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onRelatedVideoClicked(entity);
            }
        });

        return convertView;
    }

    private String transformDuration(int seconds) {
        int minute = seconds / 60;
        int second = seconds % 60;
        return getTwoLength(minute) + ":" + getTwoLength(second);
    }

    private String getTwoLength(final int data) {
        if(data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }

    public interface RelatedVideoClickListener{
        void onRelatedVideoClicked(RelatedVideoList.RelatedVideoEntity entity);
    }

    public static class ViewHolder {
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_video_length)
        TextView mVideoLength;
        @Bind(R.id.tv_play_times)
        TextView mPlayTimes;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
