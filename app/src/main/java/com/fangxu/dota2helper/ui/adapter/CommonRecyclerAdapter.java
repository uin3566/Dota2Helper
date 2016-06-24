package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/17.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerAdapter<T>.CommonViewHolder>{
    protected static final int ITEM_HEADER = 101;
    protected static final int ITEM_NORMAL = 102;
    protected static final int ITEM_FOOTER = 103;

    protected List<T> mData = new ArrayList<>();
    protected ItemClickListener mItemClickListener;

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected View mHeader = null;
    protected View mFooter = null;

    protected abstract class CommonViewHolder extends RecyclerView.ViewHolder{
        public CommonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void fillView(int position);
    }

    protected class HeaderHolder extends CommonViewHolder{
        public HeaderHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {

        }
    }

    protected class FooterHolder extends CommonViewHolder{
        public FooterHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {

        }
    }

    public CommonRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setHeader(View header) {
        mHeader = header;
        notifyDataSetChanged();
    }

    public void setFooter(View footer) {
        mFooter = footer;
        notifyDataSetChanged();
    }

    public boolean hasHeader() {
        return mHeader != null;
    }

    public boolean hasFooter() {
        return mFooter != null;
    }

    public void setData(List<T> data) {
        if (data == null || data.isEmpty()) {
            mData.clear();
        } else {
            mData = new ArrayList<>(data);
        }
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public T getItem(int position) {
        if (hasHeader() && hasFooter()) {//header and footer
            if (position <=0 || position >= getItemCount() - 1) {
                return null;
            }
            return mData.get(position - 1);
        } else if (hasHeader()) {//only header
            if (position <= 0 || position >= getItemCount()) {
                return null;
            }
            return mData.get(position - 1);
        } else if (hasFooter()) {//only footer
            if (position < 0 || position >= getItemCount() - 1) {
                return null;
            }
            return mData.get(position);
        } else {//no header and no footer
            return mData.get(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader() && position == 0) {
            return ITEM_HEADER;
        }
        if (hasFooter() && position == getItemCount() - 1) {
            return ITEM_FOOTER;
        }
        return ITEM_NORMAL;
    }

    @Override
    public int getItemCount() {
        int dataSize = mData.size();
        if (hasHeader()) {
            if (hasFooter()) {
                dataSize += 2;
            } else {
                dataSize += 1;
            }
        } else {
            if (hasFooter()) {
                dataSize += 1;
            }
        }
        return dataSize;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_HEADER) {
            return new HeaderHolder(mHeader);
        }
        if (viewType == ITEM_FOOTER) {
            return new FooterHolder(mFooter);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hasHeader() && hasFooter()) {
                        if (position == 0) {
                            mItemClickListener.onHeaderClick();
                        } else if (position == getItemCount() - 1) {
                            mItemClickListener.onFooterClick();
                        } else {
                            mItemClickListener.onItemClick(position - 1);
                        }
                    } else if (hasHeader()) {
                        if (position == 0) {
                            mItemClickListener.onHeaderClick();
                        } else {
                            mItemClickListener.onItemClick(position - 1);
                        }
                    } else if (hasFooter()) {
                        if (position == getItemCount() - 1) {
                            mItemClickListener.onFooterClick();
                        } else {
                            mItemClickListener.onItemClick(position);
                        }
                    } else {
                        mItemClickListener.onItemClick(position);
                    }
                }
            });
        }
        holder.fillView(position);
    }

    public interface ItemClickListener{
        void onItemClick(int position);
        void onHeaderClick();
        void onFooterClick();
    }
}
