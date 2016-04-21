package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.presenter.IVideoDetailView;
import com.fangxu.dota2helper.presenter.VideoDetailPresenter;
import com.fangxu.dota2helper.ui.Fragment.VideoWatchFragment;
import com.fangxu.dota2helper.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoDetailActivity extends BaseActivity implements IVideoDetailView {
    public static final String VIDEO_DATE = "video_date";
    public static final String VIDEO_VID = "video_nid";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private VideoFragmentPagerAdapter mAdapter;

    private VideoDetailPresenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mToolbar.setTitle(R.string.video_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mPresenter = new VideoDetailPresenter(this);
        queryVideoSetInfo();
    }

    private void queryVideoSetInfo() {
        String date = getIntent().getStringExtra(VIDEO_DATE);
        String vid = getIntent().getStringExtra(VIDEO_VID);
        mPresenter.queryVideoSetInformation(date, vid);
    }

    @Override
    public void setVideoSet(VideoSetList videoSetList) {
        ToastUtil.showToast(this, "success");
        mAdapter = new VideoFragmentPagerAdapter(getSupportFragmentManager());
        addFragments(videoSetList);
        mViewPager.setAdapter(mAdapter);
    }

    private void addFragments(VideoSetList videoSetList) {
        List<VideoSetList.VideoDateVidEntity> entityList = videoSetList.getList();
        for (int i = 0; i < 1; i++) {
            Bundle bundle = new Bundle();
            if (i == 0) {
                bundle.putString(VideoWatchFragment.YKVID, videoSetList.getYoukuvid());
            } else {
                bundle.putString(VideoWatchFragment.YKVID, "");
            }
            bundle.putString(VideoWatchFragment.DATE, entityList.get(i).getDate());
            bundle.putString(VideoWatchFragment.VID, entityList.get(i).getVid());
            VideoWatchFragment fragment = VideoWatchFragment.newInstance(bundle);
            mAdapter.add(fragment);
        }
    }

    @Override
    public void onGetInfoFailed(String error) {
        ToastUtil.showToast(this, error);
    }

    @Override
    public void onVideoInvalid(String invalid) {
        ToastUtil.showToast(this, invalid);
    }

    private static class VideoFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<VideoWatchFragment> mFragmentList = new ArrayList<>();

        public VideoFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void add(VideoWatchFragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
