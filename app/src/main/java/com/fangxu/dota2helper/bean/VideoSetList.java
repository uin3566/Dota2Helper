package com.fangxu.dota2helper.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 * one series of video, such as competition video round1, round2, round3
 */
public class VideoSetList {
    private List<VideoDateVidEntity> video_set;

    public List<VideoDateVidEntity> getVideoSet() {
        return video_set;
    }

    public void setVideoSet(List<VideoDateVidEntity> video_set) {
        this.video_set = video_set;
    }

    public static class VideoDateVidEntity{
        private String date;
        private String vid;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }
    }
}
