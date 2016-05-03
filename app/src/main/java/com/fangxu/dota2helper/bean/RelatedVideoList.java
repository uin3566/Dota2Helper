package com.fangxu.dota2helper.bean;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/30.
 */
public class RelatedVideoList {

    private int total;
    private List<RelatedVideoEntity> videos;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RelatedVideoEntity> getVideos() {
        return videos;
    }

    public void setVideos(List<RelatedVideoEntity> videos) {
        this.videos = videos;
    }

    public static class RelatedVideoEntity {
        private String id;
        private String title;
        private String link;
        private String thumbnail;
        private float duration;
        private String category;
        private String state;
        private int view_count;
        private int favorite_count;
        private int comment_count;
        private int up_count;
        private int down_count;
        private String published;
        private User user;
        private List<String> operation_limit;
        private List<String> streamtypes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public float getDuration() {
            return duration;
        }

        public void setDuration(float duration) {
            this.duration = duration;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getView_count() {
            return view_count;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
            this.favorite_count = favorite_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getUp_count() {
            return up_count;
        }

        public void setUp_count(int up_count) {
            this.up_count = up_count;
        }

        public int getDown_count() {
            return down_count;
        }

        public void setDown_count(int down_count) {
            this.down_count = down_count;
        }

        public String getPublished() {
            return published;
        }

        public void setPublished(String published) {
            this.published = published;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public List<String> getOperation_limit() {
            return operation_limit;
        }

        public void setOperation_limit(List<String> operation_limit) {
            this.operation_limit = operation_limit;
        }

        public List<String> getStreamtypes() {
            return streamtypes;
        }

        public void setStreamtypes(List<String> streamtypes) {
            this.streamtypes = streamtypes;
        }
    }

    public static class User{
        private String id;
        private String name;
        private String link;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
