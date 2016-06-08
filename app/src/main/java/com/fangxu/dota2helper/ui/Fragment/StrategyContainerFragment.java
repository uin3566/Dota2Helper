package com.fangxu.dota2helper.ui.Fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.util.NavUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/4.
 */
public class StrategyContainerFragment extends BaseFragment {
    @Bind(R.id.smart_tab_layout)
    SmartTabLayout mSmartTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    public static StrategyContainerFragment newInstance() {
        return new StrategyContainerFragment();
    }

    @Override
    public void init() {
        setRetainInstance(true);
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_strategy;
    }

    @Override
    public void initView(View view) {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(getActivity());
        for (int i = 0; i < NavUtil.strategyTypeList.length; i++) {
            creator.add(NavUtil.getStrategyTypeChinese(i), StrategyFragment.class);
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(),creator.create());

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(NavUtil.strategyTypeList.length);
        mSmartTabLayout.setViewPager(mViewPager);
    }
}
