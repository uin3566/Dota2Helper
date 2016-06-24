package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.ui.widget.FlipperBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/21.
 */
public class NewsAdapter2 extends CommonRecyclerAdapter<NewsList.NewsEntity> {
    public NewsAdapter2(Context context) {
        super(context);
    }

    public void updateData(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        if (!append) {
            mData.clear();
        }
        mData.addAll(newsEntityList);
        notifyDataSetChanged();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewHolder != null) {
            return viewHolder;
        }
        View view = mLayoutInflater.inflate(R.layout.item_news, parent, false);
        viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    private class NewsViewHolder extends CommonViewHolder{
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_description)
        TextView mDescription;
        @Bind(R.id.tv_time)
        TextView mTime;

        public NewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            int newPos;
            if (hasHeader()) {
                newPos = position - 1;
            } else {
                newPos = position;
            }
            if (hasFooter() && position >= getItemCount() - 1) {
                return;
            }
            NewsList.NewsEntity newsEntity = getItem(newPos);
            Glide.with(mContext).load(newsEntity.getBackground()).placeholder(R.drawable.image_background_default).into(mBackground);
            mTitle.setText(newsEntity.getTitle());
            mDescription.setText(newsEntity.getDescription());
            mTime.setText(newsEntity.getTime());
        }
    }
}
