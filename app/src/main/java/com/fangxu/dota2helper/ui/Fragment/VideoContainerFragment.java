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
 * Created by Administrator on 2016/4/19.
 */
public class VideoContainerFragment extends BaseFragment{
    @Bind(R.id.smart_tab_layout)
    SmartTabLayout mSmartTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    public void init() {
        setRetainInstance(true);
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView(View view) {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(getActivity());
        for (int i = 0; i < NavUtil.videoTypeList.length; i++) {
            creator.add(NavUtil.getVideoTypeChinese(i), VideoFragment.class);
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(),creator.create());

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(NavUtil.videoTypeList.length);
        mSmartTabLayout.setViewPager(mViewPager);
    }
}
