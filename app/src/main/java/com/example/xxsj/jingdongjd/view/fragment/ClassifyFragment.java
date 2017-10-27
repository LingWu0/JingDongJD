package com.example.xxsj.jingdongjd.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.app.MyApplication;
import com.example.xxsj.jingdongjd.bean.ClassifyTablayoutBean;
import com.example.xxsj.jingdongjd.view.adapter.MyClassifyViewpagerAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * 类的用途:仿京东商城
 * 作者:郝兵丽
 * 日期:2017/9/19
 */

public class ClassifyFragment extends Fragment {


    private View view;
    private VerticalTabLayout tabLayout;
    private ViewPager viewPager;
    private List<ClassifyTablayoutBean.DatasBean.ClassListBean> classList;
    private MyClassifyViewpagerAdapter classifyViewpagerAdapter;
    private ClassifyTablayoutBean classifyTablayoutBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classify, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    //找控件
    private void initView() {
        tabLayout = (VerticalTabLayout) view.findViewById(R.id.classify_tab);
        viewPager = (ViewPager) view.findViewById(R.id.classify_viewpager);

    }

    //使用okhttp获取网络数据
    private void initData() {
        OkHttpClient okHttpClient = MyApplication.okHttpClient();
        Request request = new Request.Builder().url("http://169.254.64.96/mobile/index.php?act=goods_class").build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            private String json;

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    json = response.body().string();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            classifyTablayoutBean = gson.fromJson(json, ClassifyTablayoutBean.class);
                            classList = classifyTablayoutBean.getDatas().getClass_list();
                            classifyViewpagerAdapter = new MyClassifyViewpagerAdapter(getActivity().getSupportFragmentManager(), classList);
                            viewPager.setAdapter(classifyViewpagerAdapter);
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    });

                }

            }
        });
    }
}
