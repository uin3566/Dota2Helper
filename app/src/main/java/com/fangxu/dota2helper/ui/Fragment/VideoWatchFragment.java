package com.fangxu.dota2helper.ui.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fangxu.dota2helper.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoWatchFragment extends BaseFragment {
    @Bind(R.id.tv_test)
    TextView mTest;

    public static final String DATE = "video_date";
    public static final String VID = "video_vid";
    public static final String YKVID = "video_ykvid";
    private String mVid;
    private String mDate;
    private String mYkvid;

    public static VideoWatchFragment newInstance(Bundle data) {
        VideoWatchFragment fragment = new VideoWatchFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void init() {
        mVid = getArguments().getString(VID);
        mDate = getArguments().getString(DATE);
        mYkvid = getArguments().getString(YKVID);
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_video_watch;
    }

    @Override
    public void initView(View view) {

    }
}
