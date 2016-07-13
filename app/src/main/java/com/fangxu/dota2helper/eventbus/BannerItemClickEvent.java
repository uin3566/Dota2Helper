package com.fangxu.dota2helper.eventbus;

import com.fangxu.dota2helper.bean.NewsList;

/**
 * Created by dear33 on 2016/7/14.
 */
public class BannerItemClickEvent {
    private NewsList.BannerEntity mBannerEntity;

    public BannerItemClickEvent(NewsList.BannerEntity bannerEntity) {
        mBannerEntity = bannerEntity;
    }
}
