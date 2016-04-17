package com.fangxu.dota2helper.util;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.ui.Fragment.NewsFragment;
import com.fangxu.dota2helper.ui.Fragment.StrategyFragment;
import com.fangxu.dota2helper.ui.Fragment.UpdateFragment;

/**
 * Created by Xuf on 2016/4/4.
 */
public class NavUtil {

    private static final String ALL = "all";
    private static final String NEWER = "newer";
    private static final String STEP = "step";
    private static final String SKILL = "skill";

    public static final int categoryList[] = {R.string.news, R.string.strategy, R.string.update};

    public static final String fragmentList[] = {NewsFragment.class.getName(), StrategyFragment.class.getName(), UpdateFragment.class.getName()};

    public static final String strategyTypeList[] = {ALL, NEWER, STEP, SKILL};
}
