package com.fangxu.dota2helper.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fangxu.dota2helper.greendao.DaoMaster;
import com.fangxu.dota2helper.greendao.DaoSession;

/**
 * Created by dear33 on 2016/7/4.
 */
public class GreenDaoHelper {
    private static final String GREEN_DB_NAME = "dota2helperdb";

    private static GreenDaoHelper instance = null;
    private DaoSession daoSession;
    private SQLiteDatabase db;

    public static GreenDaoHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (GreenDaoHelper.class) {
                if (instance == null) {
                    instance = new GreenDaoHelper(context);
                }
            }
        }
        return instance;
    }

    private GreenDaoHelper() {

    }

    private GreenDaoHelper(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, GREEN_DB_NAME, null);
                db = devOpenHelper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                daoSession = daoMaster.newSession();
            }
        }).start();
    }

    public DaoSession getSession() {
        return daoSession;
    }
}
