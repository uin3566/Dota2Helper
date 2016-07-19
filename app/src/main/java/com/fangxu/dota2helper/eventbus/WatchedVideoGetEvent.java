package com.fangxu.dota2helper.eventbus;

import com.fangxu.dota2helper.greendao.GreenWatchedVideo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
public class WatchedVideoGetEvent {
    private boolean success;
    private List<GreenWatchedVideo> mGreenWatchedVideos;

    public WatchedVideoGetEvent(boolean success, List<GreenWatchedVideo> greenWatchedVideos) {
        mGreenWatchedVideos = greenWatchedVideos;
    }
}
