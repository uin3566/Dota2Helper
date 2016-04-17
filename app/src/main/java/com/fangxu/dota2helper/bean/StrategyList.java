package com.fangxu.dota2helper.bean;

import java.util.List;

/**
 * Created by lenov0 on 2016/4/17.
 */
public class StrategyList {
    private List<StrategyEntity> strategies;

    private int nextListId;

    public int getNextListId() {
        return nextListId;
    }

    public void setNextListId(int next_list_id) {
        this.nextListId = next_list_id;
    }

    public List<StrategyEntity> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<StrategyEntity> strategies) {
        this.strategies = strategies;
    }

    public static class StrategyEntity{
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
