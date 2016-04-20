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
public class StrategyAdapter extends RecyclerView.Adapter<StrategyAdapter.StrategyViewHolder> {
    private List<StrategyList.StrategyEntity> mStrategyEntityList = new ArrayList<>();

    private ItemClickListener mItemClickListener = null;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public interface ItemClickListener{
        void onItemClick(String date, String nid);
    }

    public StrategyAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mItemClickListener = itemClickListener;
    }

    public void updateData(List<StrategyList.StrategyEntity> strategyEntityList, boolean append) {
        if (!append) {
            mStrategyEntityList.clear();
        }
        mStrategyEntityList.addAll(strategyEntityList);
    }

    @Override
    public StrategyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_strategies, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick((String)view.getTag(R.id.tag_date), (String)view.getTag(R.id.tag_id));
                }
            }
        });
        return new StrategyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StrategyViewHolder holder, int position) {
        StrategyList.StrategyEntity entity = mStrategyEntityList.get(position);
        holder.itemView.setTag(R.id.tag_date, entity.getDate());
        holder.itemView.setTag(R.id.tag_id, entity.getNid());
        holder.fillView(entity);
    }

    @Override
    public int getItemCount() {
        return mStrategyEntityList.size();
    }

    public class StrategyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_description)
        TextView mDescription;

        public StrategyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillView(StrategyList.StrategyEntity strategyEntity) {
            Glide.with(mContext).load(strategyEntity.getBackground()).placeholder(R.drawable.image_background_default).into(mBackground);
            mTitle.setText(strategyEntity.getTitle());
            mDescription.setText(strategyEntity.getDescription());
        }
    }
}
