package com.fangxu.dota2helper;

import java.util.HashMap;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenov0 on 2016/5/2.
 */
public enum RxCenter {
    INSTANCE;
    private Map<Integer, CompositeSubscription> mCompositeSubscriptionMap;

    RxCenter() {
        mCompositeSubscriptionMap = new HashMap<>();
    }

    public void addCompositeSubscription(int taskId) {
        if (mCompositeSubscriptionMap.get(taskId) == null) {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            mCompositeSubscriptionMap.put(taskId, compositeSubscription);
        }
    }

    public void removeCompositeSubscription(int taskId) {
        CompositeSubscription compositeSubscription = mCompositeSubscriptionMap.get(taskId);
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            mCompositeSubscriptionMap.remove(taskId);
        }
    }

    public CompositeSubscription getCompositeSubscription(int taskId) {
        CompositeSubscription compositeSubscription = mCompositeSubscriptionMap.get(taskId);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubscriptionMap.put(taskId, compositeSubscription);
        }
        return compositeSubscription;
    }
}
