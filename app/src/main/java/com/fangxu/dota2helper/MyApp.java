package com.fangxu.dota2helper;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.fangxu.dota2helper.util.NavUtil;
import com.squareup.leakcanary.LeakCanary;
import com.youku.player.YoukuPlayerBaseConfiguration;

/**
 * Created by Xuf on 2016/4/3.
 */
public class MyApp extends Application {
    public static YoukuPlayerBaseConfiguration configuration;
    private static String youkuClientId = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        initYoukuConfiguration();
        initClientId();
        NavUtil.init(this);
    }

    public static String getYoukuClientId() {
        return youkuClientId;
    }

    private void initClientId() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo != null) {
            youkuClientId = appInfo.metaData.getString("client_id");
        }
    }

    private void initYoukuConfiguration() {
        configuration = new YoukuPlayerBaseConfiguration(this) {
            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                return null;
            }

            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                return null;
            }

            @Override
            public String configDownloadPath() {
                return null;
            }
        };
    }
}
