package com.example.xxsj.jingdongjd.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.app.MyApplication;
import com.example.xxsj.jingdongjd.bean.ClassifyRecyclerviewTextBean;
import com.example.xxsj.jingdongjd.view.adapter.MyClassifyRecyclerAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类的用途:仿京东商城
 * 作者:郝兵丽
 * 日期:2017/9/19
 */

public class ClassifyViewPagerFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classify_viewpager_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.classify_recyclerview);
    }

    private void initData() {
        final String classifytabid = getArguments().getString("classifytabid");
        System.out.println("===============" + classifytabid);
        OkHttpClient okHttpClient = MyApplication.okHttpClient();
        Request request = new Request.Builder()
                .url("http://169.254.64.96/mobile/index.php?act=goods_class&gc_id=" + classifytabid)
                .build();
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
                            ClassifyRecyclerviewTextBean classifyRecyclerviewTextBean = gson.fromJson(json, ClassifyRecyclerviewTextBean.class);
                            List<ClassifyRecyclerviewTextBean.DatasBean.ClassListBean> classtext_list = classifyRecyclerviewTextBean.getDatas().getClass_list();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            MyClassifyRecyclerAdapter classifyRecyclerAdapter = new MyClassifyRecyclerAdapter(getActivity(), classtext_list);
                            recyclerView.setAdapter(classifyRecyclerAdapter);
                        }
                    });

                }
            }
        });
    }
}
