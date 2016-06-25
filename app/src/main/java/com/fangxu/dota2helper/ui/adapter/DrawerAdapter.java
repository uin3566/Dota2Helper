package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.util.NavUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/21.
 */
public class DrawerAdapter extends CommonRecyclerAdapter<Integer> {
    private final Integer[] iconId = new Integer[]{R.drawable.selector_news, R.drawable.selector_strategy,
            R.drawable.selector_update, R.drawable.selector_videos};

    private int mCurrentPos = 0;

    public DrawerAdapter(Context context) {
        super(context);
    }

    public void setCurrentPos(int currentPos) {
        mCurrentPos = currentPos;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        List<Integer> data = Arrays.asList(iconId);
        setData(data);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_drawer_item, parent, false);
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public class DrawerViewHolder extends CommonViewHolder{
        @Bind(R.id.ll_drawer_item)
        LinearLayout mDrawerItem;
        @Bind(R.id.iv_drawer_icon)
        ImageView mItemImageView;
        @Bind(R.id.tv_drawer_text)
        TextView mItemTextView;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void fillView(int position) {
            mItemImageView.setImageResource(getItem(position));
            mItemTextView.setText(NavUtil.categoryList[position]);
            if (position == mCurrentPos) {
                mDrawerItem.setSelected(true);
            } else {
                mDrawerItem.setSelected(false);
            }
        }
    }
}
