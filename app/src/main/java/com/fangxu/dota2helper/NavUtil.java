package com.fangxu.dota2helper;

import com.fangxu.dota2helper.ui.Fragment.NewsFragment;
import com.fangxu.dota2helper.ui.Fragment.StrategyFragment;
import com.fangxu.dota2helper.ui.Fragment.UpdateFragment;

/**
 * Created by Xuf on 2016/4/4.
 */
public class NavUtil {

    public static final int category_list[] = {R.string.news, R.string.update, R.string.strategy};

    public static final String fragment_list[] = {NewsFragment.class.getName(), UpdateFragment.class.getName(), StrategyFragment.class.getName()};
}
