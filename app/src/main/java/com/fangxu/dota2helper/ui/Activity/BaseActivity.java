package com.fangxu.dota2helper.ui.Activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.RxCenter;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by Xuf on 2016/4/3.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getLayoutResId();
    public abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemBarTintDrawable(getResources().getDrawable(R.drawable.drawable_primary));
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
