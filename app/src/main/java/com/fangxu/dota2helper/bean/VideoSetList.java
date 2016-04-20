package com.fangxu.dota2helper.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 * one series of video, such as competition video round1, round2, round3
 */
public class VideoSetList {
    private int isvalid;
    private String youkuvid;
    private List<VideoDateVidEntity> list;

    public List<VideoDateVidEntity> getList() {
        return list;
    }

    public void setList(List<VideoDateVidEntity> list) {
        this.list = list;
    }

    public int getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public String getYoukuvid() {
        return youkuvid;
    }

    public void setYoukuvid(String youkuvid) {
        this.youkuvid = youkuvid;
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
