package com.example.xxsj.jingdongjd.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MyHomeViewPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> views;

    public MyHomeViewPagerAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> views) {
        super(supportFragmentManager);
        this.views=views;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
