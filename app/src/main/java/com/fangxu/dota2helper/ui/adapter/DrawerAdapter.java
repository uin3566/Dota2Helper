package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.util.NavUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Xuf on 2016/4/3.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private LayoutInflater mLayoutInflater;

    private ItemClickListener mItemClickListener = null;

    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public DrawerAdapter(Context context, ItemClickListener itemClickListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemClickListener = itemClickListener;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_drawer_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick((int)v.getTag());
                }
            }
        });
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder drawerViewHolder, int position) {
        drawerViewHolder.setData(position);
        drawerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return NavUtil.categoryList.length;
    }

    public class DrawerViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_drawer_item)
        TextView mItemTextView;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            mItemTextView.setText(NavUtil.categoryList[position]);
        }
    }
}
