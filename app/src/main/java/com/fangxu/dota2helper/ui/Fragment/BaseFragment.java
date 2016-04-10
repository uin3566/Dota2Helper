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
public abstract class BaseFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipe_refresh)
    SwipeToLoadLayout mSwipeRefresh;

    public abstract int getResourceId();
    public abstract void initView();
    public abstract void init();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getResourceId(), container, false);
        ButterKnife.bind(this, view);

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setOnLoadMoreListener(this);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
