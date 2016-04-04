package com.fangxu.dota2helper.ui.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.fangxu.dota2helper.NavUtil;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.ui.Fragment.BaseFragment;
import com.fangxu.dota2helper.ui.Fragment.NewsFragment;
import com.fangxu.dota2helper.ui.adapter.DrawerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

    private DrawerAdapter mDrawerAdapter;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private Map<Integer, String> mFragmentNameMap = new HashMap<>();
    private Map<String, BaseFragment> mFragmentMap = new HashMap<>();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        setSupportActionBar(mToolbar);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mDrawerAdapter = new DrawerAdapter(this, this);
        mRecyclerView.setAdapter(mDrawerAdapter);

        initFragmentNameMap();
        BaseFragment fragment = getFragmentByName(mFragmentNameMap.get(NavUtil.category_list[0]));
        setFragment(fragment);
    }

    @Override
    public void onItemClick(int position) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        BaseFragment fragment = getFragmentByName(mFragmentNameMap.get(NavUtil.category_list[position]));
        setFragment(fragment);
    }

    private void initFragmentNameMap() {
        int len = NavUtil.category_list.length;
        for (int i = 0; i < len; i++) {
            mFragmentNameMap.put(NavUtil.category_list[i], NavUtil.fragment_list[i]);
        }
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

    private BaseFragment getFragmentByName(String name) {
        BaseFragment fragment = mFragmentMap.get(name);
        if (fragment == null) {
            try{
                fragment = (BaseFragment)Class.forName(name).newInstance();
            } catch (Exception e) {
                fragment = NewsFragment.newInstance();
            }
            mFragmentMap.put(name, fragment);
        }
        return fragment;
    }
}
