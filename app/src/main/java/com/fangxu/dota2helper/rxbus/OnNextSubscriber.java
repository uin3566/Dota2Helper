package com.fangxu.dota2helper.rxbus;

import rx.Subscriber;

/**
 * Created by dear33 on 2016/8/11.
 */
public abstract class OnNextSubscriber<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
}
