package com.fangxu.dota2helper.ui.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangxu.dota2helper.R;

import java.util.Random;

import butterknife.Bind;

/**
 * Created by dear33 on 2016/6/26.
 */
public class SplashActivity extends BaseActivity {
    @Bind(R.id.splash_image)
    ImageView mSplashImage;
    @Bind(R.id.splash_version_name)
    TextView mVersionName;
    @Bind(R.id.splash_copyright)
    TextView mCopyright;

    private static final int[] splashResId = {R.drawable.splash1, R.drawable.splash2, R.drawable.splash3, R.drawable.splash4};

    @Override
    public boolean applySystemBarDrawable() {
        return false;
    }

    @Override
    public int getTitleResId() {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mCopyright.setText(getCopyright());
        mVersionName.setText(getVersionName());
        mSplashImage.setImageResource(getSplashResId());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashImage.startAnimation(animation);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onBackPressed() {

    }

    private int getSplashResId() {
        int resId = new Random(System.currentTimeMillis()).nextInt(splashResId.length);
        return splashResId[resId];
    }

    public String getVersionName() {
        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.format(getResources().getString(R.string.splash_version), versionName);
    }

    public String getCopyright() {
        return getResources().getString(R.string.splash_copyright);
    }
}
