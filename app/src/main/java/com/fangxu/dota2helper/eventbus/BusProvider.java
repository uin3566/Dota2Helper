package com.fangxu.dota2helper.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by Xuf on 2016/1/23.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    private BusProvider(){

    }

    public static Bus getInstance(){
        return BUS;
    }
}
