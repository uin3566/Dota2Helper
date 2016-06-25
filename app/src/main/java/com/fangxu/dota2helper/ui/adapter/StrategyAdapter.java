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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenov0 on 2016/4/17.
 */
public class StrategyAdapter extends CommonRecyclerAdapter<StrategyList.StrategyEntity> {
    public StrategyAdapter(Context context) {
        super(context);
    }

    public void updateData(List<StrategyList.StrategyEntity> strategyEntityList, boolean append) {
        if (!append) {
            mData.clear();
        }
        mData.addAll(strategyEntityList);
        notifyDataSetChanged();
    }

    @Override
    public StrategyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_strategies, parent, false);
        return new StrategyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public class StrategyViewHolder extends CommonViewHolder{
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_description)
        TextView mDescription;

        public StrategyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            StrategyList.StrategyEntity strategyEntity = getItem(position);
            Glide.with(mContext).load(strategyEntity.getBackground()).placeholder(R.drawable.image_background_default).into(mBackground);
            mTitle.setText(strategyEntity.getTitle());
            mDescription.setText(strategyEntity.getDescription());
        }
    }
}
