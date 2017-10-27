package com.example.xxsj.jingdongjd.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.view.activity.LoginActivity;

/**
 * 类的用途:仿京东商城
 * 作者:郝兵丽
 * 日期:2017/9/19
 */

public class MineFragment extends Fragment implements View.OnClickListener{

    private View view;
    private RelativeLayout loginOrRegister;
    private ImageView ivLoginOrRegister;
    private TextView tvLoginOrRegister;
    private String name;
    private String iconurl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loginOrRegister.setOnClickListener(this);

    }
    //找到控件
    private void initView(){
        loginOrRegister = (RelativeLayout) view.findViewById(R.id.rel_LoginOrRegister);
        ivLoginOrRegister = (ImageView) view.findViewById(R.id.Iv_LoginOrRegister);
        tvLoginOrRegister = (TextView) view.findViewById(R.id.tv_LoginOrRegister);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.rel_LoginOrRegister:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getActivity().getSharedPreferences("qqdata", Context.MODE_PRIVATE);
        name = sp.getString("name", "登陆/注册");
        iconurl = sp.getString("iconurl", "1");
        if (iconurl.equals("1")){
            ivLoginOrRegister.setImageResource(R.drawable.b3h);
        }else {
            Glide.with(this).load(iconurl).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivLoginOrRegister) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivLoginOrRegister.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
        tvLoginOrRegister.setText(name);


    }
}
