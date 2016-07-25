package com.fangxu.dota2helper.ui.Fragment;

import android.support.v4.app.Fragment;

import com.fangxu.dota2helper.util.NavUtil;

/**
 * Created by Administrator on 2016/4/19.
 */
public class VideoContainerFragment extends BaseSmartTabFragment {
    @Override
    protected int getTabCount() {
        return NavUtil.videoTypeList.length;
    }

    @Override
    protected String getTabTitle(int position) {
        return NavUtil.getVideoTypeChinese(position);
    }

    @Override
    protected Class<? extends Fragment> getViewPagerItemFragmentClass() {
        return VideoFragment.class;
    }
}
