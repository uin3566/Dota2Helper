package com.fangxu.dota2helper;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.fangxu.dota2helper.greendao");
        addNews(schema);
        addStrategy(schema);
        addUpdate(schema);
        addVideo(schema);
        addHistoryVideo(schema);
        new DaoGenerator().generateAll(schema, "../Dota2Helper/app/src/main/java-gen");
    }

    public static void addHistoryVideo(Schema schema) {
        Entity video = schema.addEntity("GreenWatchedVideo");
        video.addStringProperty("videoyoukuvid").primaryKey();
        video.addStringProperty("videobackground");
        video.addStringProperty("videotitle");
        video.addLongProperty("videowatchtime");
        video.addIntProperty("videoduration");
        video.addIntProperty("videoplaytime");
        video.addBooleanProperty("videoEnded");
    }

    public static void addNews(Schema schema) {
        Entity news = schema.addEntity("GreenNews");
        news.addIdProperty();
        news.addStringProperty("newslistjson");
    }

    public static void addVideo(Schema schema) {
        Entity videos = schema.addEntity("GreenVideo");
        videos.addIdProperty();
        videos.addStringProperty("videolistjson");
        videos.addStringProperty("videotype");
    }

    public static void addStrategy(Schema schema) {
        Entity strategies = schema.addEntity("GreenStrategy");
        strategies.addIdProperty();
        strategies.addStringProperty("strategylistjson");
        strategies.addStringProperty("strategytype");
    }

    public static void addUpdate(Schema schema) {
        Entity updates = schema.addEntity("GreenUpdate");
        updates.addIdProperty();
        updates.addStringProperty("updatelistjson");
    }
}
