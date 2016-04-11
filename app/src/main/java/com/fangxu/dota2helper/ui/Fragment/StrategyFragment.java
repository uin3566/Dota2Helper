package com.fangxu.dota2helper.ui.Fragment;

import android.support.v4.view.ViewPager;

import com.fangxu.dota2helper.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/4.
 */
public class StrategyFragment extends TabBaseFragment {
    @Bind(R.id.smart_tab_layout)
    SmartTabLayout mSmartTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    public static StrategyFragment newInstance() {
        return new StrategyFragment();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_strategy;
    }

    @Override
    public void initView() {

    }
}
