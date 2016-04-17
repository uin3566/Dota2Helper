package com.fangxu.dota2helper.ui.Fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/4.
 */
public class StrategyFragment extends BaseFragment {
    @Bind(R.id.smart_tab_layout)
    SmartTabLayout mSmartTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    public static StrategyFragment newInstance() {
        return new StrategyFragment();
    }

    @Override
    public void init() {
        setRetainInstance(true);
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_strategy;
    }

    @Override
    public void initView(View view) {
        String[] strategyClass = getActivity().getResources().getStringArray(R.array.strategy_type);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(),
                FragmentPagerItems.with(getActivity()).
                add(strategyClass[0], StrategyContentFragment.class).
                add(strategyClass[1], StrategyContentFragment.class).
                add(strategyClass[2], StrategyContentFragment.class).
                add(strategyClass[3], StrategyContentFragment.class).
                create());

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(strategyClass.length);
        mSmartTabLayout.setViewPager(mViewPager);
    }
}
