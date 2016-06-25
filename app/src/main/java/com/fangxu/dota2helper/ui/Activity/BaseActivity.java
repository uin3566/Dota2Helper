package com.fangxu.dota2helper.ui.Activity;

import android.annotation.TargetApi;
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

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
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

    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setTintDrawable(tintDrawable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
