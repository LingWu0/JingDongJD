package com.example.xxsj.jingdongjd.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.xxsj.jingdongjd.view.fragment.TabFragment;


/**
 * Created by Administrator on 2017/9/7.
 */

public class MyTabViewpagerAdapter extends FragmentPagerAdapter{
    private String[] titles;
    public MyTabViewpagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text",titles[position]);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
