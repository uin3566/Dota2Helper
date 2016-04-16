package com.fangxu.dota2helper.bean;

import java.util.List;

/**
 * Created by Xuf on 2016/4/6.
 */
public class NewsList {

    private List<NewsEntity> news;

    private int nextListId;

    public int getNextListId() {
        return nextListId;
    }

    public void setNextListId(int next_list_id) {
        this.nextListId = next_list_id;
    }

    public List<NewsEntity> getNews() {
        return news;
    }

    public void setNews(List<NewsEntity> news) {
        this.news = news;
    }

    public static class NewsEntity{
        private String time;
        private String description;
        private String background;
        private String title;
        private String date;
        private String nid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }
    }
}
