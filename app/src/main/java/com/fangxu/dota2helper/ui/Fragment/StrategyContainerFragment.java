package com.fangxu.dota2helper.ui.Fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.fangxu.dota2helper.util.NavUtil;

/**
 * Created by Xuf on 2016/4/4.
 */
public class StrategyContainerFragment extends BaseSmartTabFragment {
    @Override
    public void initView(View view) {
        mSmartTabLayout.setDistributeEvenly(true);
        super.initView(view);
    }

    @Override
    protected int getTabCount() {
        return NavUtil.strategyTypeList.length;
    }

    @Override
    protected String getTabTitle(int position) {
        return NavUtil.getStrategyTypeChinese(position);
    }

    @Override
    protected Class<? extends Fragment> getViewPagerItemFragmentClass() {
        return StrategyFragment.class;
    }
}
