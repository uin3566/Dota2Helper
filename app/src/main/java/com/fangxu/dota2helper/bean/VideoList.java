package com.fangxu.dota2helper.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 * video list
 */
public class VideoList {
    private List<VideoEntity> videos;

    public List<VideoEntity> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoEntity> videos) {
        this.videos = videos;
    }

    public static class VideoEntity{
        private String ykvid;
        private String vid;
        private String title;
        private String publishin;
        private String videolength;
        private String background;
        private String date;

        public String getYkvid() {
            return ykvid;
        }

        public void setYkvid(String ykvid) {
            this.ykvid = ykvid;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublishin() {
            return publishin;
        }

        public void setPublishin(String publishin) {
            this.publishin = publishin;
        }

        public String getVideolength() {
            return videolength;
        }

        public void setVideolength(String videolength) {
            this.videolength = videolength;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
