package com.fangxu.dota2helper.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.fangxu.dota2helper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Xuf on 2016/4/3.
 */
public abstract class RefreshBaseFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipe_refresh)
    SwipeToLoadLayout mSwipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setOnLoadMoreListener(this);
        return view;
    }
}
