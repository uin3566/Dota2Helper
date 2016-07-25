package com.fangxu.dota2helper.ui.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/25.
 */
public abstract class BaseSmartTabFragment extends BaseFragment {
    @Bind(R.id.smart_tab_layout)
    SmartTabLayout mSmartTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    protected abstract int getTabCount();

    protected abstract String getTabTitle(int position);

    protected abstract Class<? extends Fragment> getViewPagerItemFragmentClass();

    @Override
    public void init() {
        setRetainInstance(true);
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_smarttab_viewpager;
    }

    @Override
    public void initView(View view) {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(getActivity());
        for (int i = 0, tabCount = getTabCount(); i < tabCount; i++) {
            creator.add(getTabTitle(i), getViewPagerItemFragmentClass());
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), creator.create());

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(getTabCount());
        mSmartTabLayout.setViewPager(mViewPager);
    }
}
