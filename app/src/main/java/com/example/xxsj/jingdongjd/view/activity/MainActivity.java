package com.example.xxsj.jingdongjd.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.xxsj.jingdongjd.R;

import com.example.xxsj.jingdongjd.view.fragment.CarFragment;
import com.example.xxsj.jingdongjd.view.fragment.ClassifyFragment;
import com.example.xxsj.jingdongjd.view.fragment.FindFragment;
import com.example.xxsj.jingdongjd.view.fragment.HomeFragment;
import com.example.xxsj.jingdongjd.view.fragment.MineFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioGroup radioGroup;
    private FrameLayout frameLayout;
    private Fragment[] fragments;
    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        initFragment();
        //替换Fragment的方法,设置默认第一fragment
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    if (radioButton.isChecked()) {
                        setFragmentHideOrShow(i);
                    }
                }
            }
        });


    }

    private void initFragment() {
        HomeFragment homeFragment = new HomeFragment();
        ClassifyFragment classifyFragment = new ClassifyFragment();
        FindFragment findFragment = new FindFragment();
        CarFragment carFragment = new CarFragment();
        MineFragment mineFragment = new MineFragment();
        //添加到数组
        fragments = new Fragment[]{homeFragment, classifyFragment, findFragment, carFragment, mineFragment};
        //开启事务
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //添加首页
        transaction.add(R.id.framelayout, homeFragment).commit();
        //默认设置第一个fragment显示
        setFragmentHideOrShow(0);
    }

    //显示隐藏fragment的方法
    private void setFragmentHideOrShow(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //隐藏
        ft.hide(fragments[mIndex]);
        //判断是否添加
        if (!fragments[index].isAdded()) {
            ft.add(R.id.framelayout, fragments[index]).show(fragments[index]);
        } else {
            ft.show(fragments[index]);
        }
        if (index == 0) {
           ((HomeFragment) fragments[index]).getActivity();
        }

        ft.commit();
        //再次赋值
        mIndex = index;
    }

    @Override
    public void onClick(View v) {

    }


}
