package com.fangxu.dota2helper.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/20.
 */
public enum  VideoIdCache {
    INSTANCE;

    private Map<String, String> mVidMap = new HashMap<>();

    public void add(String dota2Vid, String youkuVid) {
        mVidMap.put(dota2Vid, youkuVid);
    }

    public String get(String dota2Vid) {
        return mVidMap.containsKey(dota2Vid) ? mVidMap.get(dota2Vid) : null;
    }

    public void remove(String dota2Vid) {
        mVidMap.remove(dota2Vid);
    }

    public void clear() {
        mVidMap.clear();
    }
}
