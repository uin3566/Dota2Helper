package com.fangxu.dota2helper;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(1, "com.fangxu.dota2helper.greendao");
        addNews(schema);
        addStrategy(schema);
        addUpdate(schema);
        addVideo(schema);
        new DaoGenerator().generateAll(schema, "../Dota2Helper/app/src/main/java-gen");
    }

    public static void addNews(Schema schema) {
        Entity news = schema.addEntity("GreenNews");
        news.addIdProperty();
        news.addStringProperty("newslistjson");
    }

    public static void addVideo(Schema schema) {
        Entity news = schema.addEntity("GreenVideo");
        news.addIdProperty();
        news.addStringProperty("videolistjson");
        news.addStringProperty("videotype");
    }

    public static void addStrategy(Schema schema) {
        Entity news = schema.addEntity("GreenStrategy");
        news.addIdProperty();
        news.addStringProperty("strategylistjson");
        news.addStringProperty("strategytype");
    }

    public static void addUpdate(Schema schema) {
        Entity news = schema.addEntity("GreenUpdate");
        news.addIdProperty();
        news.addStringProperty("updatelistjson");
    }
}
