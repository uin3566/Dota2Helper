package com.fangxu.dota2helper.presenter;

import com.fangxu.dota2helper.interactor.BaseInteractor;

/**
 * Created by dear33 on 2016/6/6.
 */
public abstract class BasePresenter {
    protected BaseInteractor mInteractor;

    public void destroy() {
        mInteractor.destroy();
    }
}
