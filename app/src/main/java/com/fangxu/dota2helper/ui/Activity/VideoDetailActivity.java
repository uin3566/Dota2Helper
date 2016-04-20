package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.presenter.IVideoDetailView;
import com.fangxu.dota2helper.presenter.VideoDetailPresenter;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoDetailActivity extends BaseActivity implements IVideoDetailView{

    private VideoDetailPresenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }
}
