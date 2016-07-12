package com.fangxu.dota2helper.ui.Activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    protected Toolbar mToolbar;

    protected abstract int getLayoutResId();
    protected abstract void init(Bundle savedInstanceState);
    protected abstract boolean applySystemBarDrawable();
    protected abstract int getTitleResId();

    protected boolean applyTranslucentStatus() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(applyTranslucentStatus());
        if (applySystemBarDrawable()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.drawable_primary));
        }
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (null != mToolbar) {
            if (getTitleResId() != 0) {
                mToolbar.setTitle(getTitleResId());
            }
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init(savedInstanceState);
    }

    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    private void setSystemBarTintDrawable(Drawable tintDrawable) {
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

    private void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
