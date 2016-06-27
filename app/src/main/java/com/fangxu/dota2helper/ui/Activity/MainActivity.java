package com.fangxu.dota2helper.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fangxu.dota2helper.ui.adapter.CommonRecyclerAdapter;
import com.fangxu.dota2helper.ui.adapter.DrawerAdapter;
import com.fangxu.dota2helper.util.NavUtil;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.ui.Fragment.NewsFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Xuf on 2016/4/3.
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.rv_drawer_recycler)
    RecyclerView mRecyclerView;

    private Fragment mCurrentShowFragment = null;

    private int mCurrentDrawerPos = 0;

    private Map<Integer, String> mFragmentNameMap = new HashMap<>();

    @Override
    public boolean applySystemBarDrawable() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public int getTitleResId() {
        return NavUtil.categoryList[mCurrentDrawerPos];
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mToolbar.setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mToolbar.setTitle(NavUtil.categoryList[mCurrentDrawerPos]);
            }
        };
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        final DrawerAdapter drawerAdapter = new DrawerAdapter(this);
        drawerAdapter.setItemClickListener(new CommonRecyclerAdapter.ItemClickListener() {
            @Override
            public void onHeaderClick() {

            }

            @Override
            public void onFooterClick() {

            }

            @Override
            public void onItemClick(int position) {
                drawerAdapter.setCurrentPos(position);
                mCurrentDrawerPos = position;
                mDrawerLayout.closeDrawer(GravityCompat.START);
                String tag = mFragmentNameMap.get(NavUtil.categoryList[position]);
                Fragment fragment = getFragmentByName(tag);
                showFragment(mCurrentShowFragment, fragment, tag);
            }
        });
        mRecyclerView.setAdapter(drawerAdapter);

        initFragmentNameMap();
        String tag = mFragmentNameMap.get(NavUtil.categoryList[0]);
        Fragment fragment = getFragmentByName(tag);
        showFragment(mCurrentShowFragment, fragment, tag);
    }

    @OnClick(R.id.ll_drawer_profile)
    public void clickProfile(View mineLayout) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void initFragmentNameMap() {
        int len = NavUtil.categoryList.length;
        for (int i = 0; i < len; i++) {
            mFragmentNameMap.put(NavUtil.categoryList[i], NavUtil.fragmentList[i]);
        }
    }

    private void showFragment(Fragment from, Fragment to, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            if (to.isAdded()) {
                transaction.show(to).commit();
            } else {
                transaction.add(R.id.fl_content, to, tag).commit();
            }
        } else {
            if (to.isAdded()) {
                transaction.hide(from).show(to).commit();
            } else {
                transaction.hide(from).add(R.id.fl_content, to, tag).commit();
            }
        }
        mCurrentShowFragment = to;
    }

    private Fragment getFragmentByName(String name) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
        if (fragment != null) {
            return fragment;
        } else {
            try {
                fragment = (Fragment) Class.forName(name).newInstance();
            } catch (Exception e) {
                fragment = NewsFragment.newInstance();
            }
        }
        return fragment;
    }
}
