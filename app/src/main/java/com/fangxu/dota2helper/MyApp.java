package com.fangxu.dota2helper;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.youku.player.YoukuPlayerBaseConfiguration;

/**
 * Created by Xuf on 2016/4/3.
 */
public class MyApp extends Application {
    public static YoukuPlayerBaseConfiguration configuration;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        initYoukuConfiguration();
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
