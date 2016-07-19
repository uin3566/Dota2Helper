package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/17.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerAdapter<T>.CommonViewHolder> {
    protected static final int ITEM_HEADER = 101;
    protected static final int ITEM_NORMAL = 102;
    protected static final int ITEM_FOOTER = 103;

    protected List<T> mData = new ArrayList<>();
    protected ItemClickListener mItemClickListener;

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    private boolean mHasHeader;
    private boolean mHasFooter;

    //简单防抖动
    private static final int quickClickInterval = 800;
    private long mClickHeaderTime;
    private long mClickItemTime;
    private long mClickFooterTime;

    protected abstract class CommonViewHolder extends RecyclerView.ViewHolder {
        public CommonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void fillView(int position);
    }

    public CommonRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    protected void setHasHeader(boolean hasHeader) {
        mHasHeader = hasHeader;
    }

    protected void setHasFooter(boolean hasFooter) {
        mHasFooter = hasFooter;
    }

    protected void onClickHeader() {

    }

    protected void onClickFooter() {

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
        if (mHasHeader && mHasFooter) {//header and footer
            if (position <= 0 || position >= getItemCount() - 1) {
                return null;
            }
            return mData.get(position - 1);
        } else if (mHasHeader) {//only header
            if (position <= 0 || position >= getItemCount()) {
                return null;
            }
            return mData.get(position - 1);
        } else if (mHasFooter) {//only footer
            if (position < 0 || position >= getItemCount() - 1) {
                return null;
            }
            return mData.get(position);
        } else {//no header and no footer
            return mData.get(position);
        }
    }

    private void headerClick() {
        if (System.currentTimeMillis() - mClickHeaderTime <= quickClickInterval) {
            return;
        }
        mItemClickListener.onHeaderClick();
        onClickHeader();
        mClickHeaderTime = System.currentTimeMillis();
    }

    private void itemClick(int position) {
        if (System.currentTimeMillis() - mClickItemTime <= quickClickInterval) {
            return;
        }
        mItemClickListener.onItemClick(position);
        mClickItemTime = System.currentTimeMillis();
    }

    private void footerClick() {
        if (System.currentTimeMillis() - mClickFooterTime <= quickClickInterval) {
            return;
        }
        mItemClickListener.onFooterClick();
        onClickFooter();
        mClickFooterTime = System.currentTimeMillis();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHasHeader && position == 0) {
            return ITEM_HEADER;
        }
        if (mHasFooter && position == getItemCount() - 1) {
            return ITEM_FOOTER;
        }
        return ITEM_NORMAL;
    }

    @Override
    public int getItemCount() {
        int dataSize = mData.size();
        if (mHasHeader) {
            if (mHasFooter) {
                dataSize += 2;
            } else {
                dataSize += 1;
            }
        } else {
            if (mHasFooter) {
                dataSize += 1;
            }
        }
        return dataSize;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean clicked = false;
                    if (mHasHeader && mHasFooter) {
                        if (position == 0) {
                            clicked = true;
                            headerClick();
                        } else if (position == getItemCount() - 1) {
                            clicked = true;
                            footerClick();
                        }
                    } else if (mHasHeader) {
                        if (position == 0) {
                            clicked = true;
                            headerClick();
                        }
                    } else if (mHasFooter) {
                        if (position == getItemCount() - 1) {
                            clicked = true;
                            footerClick();
                        }
                    }
                    if (!clicked) {
                        itemClick(position);
                    }
                }
            });
        }
        holder.fillView(position);
    }

    public interface ItemClickListener {
        void onItemClick(int position);

        void onHeaderClick();

        void onFooterClick();
    }
}
