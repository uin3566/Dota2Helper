package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.ui.adapter.BaseVideoListAdapter;
import com.fangxu.dota2helper.ui.widget.CountButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dear33 on 2016/7/27.
 */
public abstract class BaseVideoListActivity extends BaseActivity {
    @Bind(R.id.recycler_watched_videos)
    RecyclerView mRecyclerView;
    @Bind(R.id.fl_empty_view)
    FrameLayout mEmptyView;
    @Bind(R.id.ll_select_delete_controllers)
    LinearLayout mSelectDeleteControllers;
    @Bind(R.id.cb_delete)
    CountButton mDeleteButton;

    protected boolean mIsEditState = false;
    protected BaseVideoListAdapter mAdapter;

    protected abstract boolean menuEditEnable();

    @OnClick(R.id.tv_select_all)
    public void clickSelectAll(View selectAll) {
        mAdapter.selectAll();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_edit && menuEditEnable()) {
                    mIsEditState = !mIsEditState;
                    updateMenuTitle(item);
                    mAdapter.updateState(mIsEditState);
                    mSelectDeleteControllers.setVisibility(mIsEditState ? View.VISIBLE : View.GONE);
                }
                return true;
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int videoCount = mAdapter.getDataItemCount();
                int deleteCount = mAdapter.deleteSelected();
                if (videoCount == deleteCount) {
                    //delete all
                    mSelectDeleteControllers.setVisibility(View.GONE);
                    mIsEditState = false;
                    updateMenuTitle(mToolbar.getMenu().getItem(0));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_watched_video, menu);
        return true;
    }

    private void updateMenuTitle(MenuItem item) {
        if (mIsEditState) {
            item.setTitle(R.string.cancel);
        } else {
            item.setTitle(R.string.edit);
        }
    }

    @Override
    protected boolean applySystemBarDrawable() {
        return true;
    }
}
