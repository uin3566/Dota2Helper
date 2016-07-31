package com.fangxu.dota2helper.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;

import com.fangxu.dota2helper.R;

/**
 * Created by dear33 on 2016/7/31.
 */
public class FullScreenVideoActivity extends BaseVideoActivity {

    public static void startFullScreenVideoActivity(Activity from, String vid, String backgroundUrl) {
        Intent intent = new Intent(from, FullScreenVideoActivity.class);
        intent.putExtra(VIDEO_YOUKU_VID, vid);
        intent.putExtra(VIDEO_BACKGROUND, backgroundUrl);
        from.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_full_screen_video;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void autoPlay() {
        mBlurImageContainer.setVisibility(View.GONE);
        if (mVid != null) {
            mYoukuPlayer.playLocalVideo(mVid);
        }
    }
}
