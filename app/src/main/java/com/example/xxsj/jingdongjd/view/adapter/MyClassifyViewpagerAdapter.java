package com.example.xxsj.jingdongjd.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.xxsj.jingdongjd.bean.ClassifyTablayoutBean;
import com.example.xxsj.jingdongjd.view.fragment.ClassifyViewPagerFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class MyClassifyViewpagerAdapter extends FragmentPagerAdapter {
    private List<ClassifyTablayoutBean.DatasBean.ClassListBean> titles;
    public MyClassifyViewpagerAdapter(FragmentManager fm, List<ClassifyTablayoutBean.DatasBean.ClassListBean> titles) {
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        ClassifyViewPagerFragment classifyViewPagerFragment = new ClassifyViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("classifytabid",titles.get(position).getGc_id());
        classifyViewPagerFragment.setArguments(bundle);
        return classifyViewPagerFragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getGc_name();
    }
}
