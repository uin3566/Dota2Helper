package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fangxu.dota2helper.ui.Fragment.BaseFragment;
import com.fangxu.dota2helper.util.NavUtil;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.ui.Fragment.NewsFragment;
import com.fangxu.dota2helper.ui.adapter.DrawerAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Xuf on 2016/4/3.
 */
public class MainActivity extends BaseActivity implements DrawerAdapter.ItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.rv_drawer_recycler)
    RecyclerView mRecyclerView;

    private Fragment mCurrentShowFragment = null;

    private DrawerAdapter mDrawerAdapter;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private Map<Integer, String> mFragmentNameMap = new HashMap<>();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mDrawerAdapter = new DrawerAdapter(this, this);
        mRecyclerView.setAdapter(mDrawerAdapter);

        initFragmentNameMap();
        String tag = mFragmentNameMap.get(NavUtil.categoryList[0]);
        Fragment fragment = getFragmentByName(tag);
        showFragment(mCurrentShowFragment, fragment, tag);
    }

    @Override
    public void onItemClick(int position) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        String tag = mFragmentNameMap.get(NavUtil.categoryList[position]);
        Fragment fragment = getFragmentByName(tag);
        showFragment(mCurrentShowFragment, fragment, tag);
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
