package com.fangxu.dota2helper.interactor;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by dear33 on 2016/6/6.
 */
public class BaseInteractor {
    protected CompositeSubscription mCompositeSubscription;

    public void destroy() {
        mCompositeSubscription = null;
    }
}
