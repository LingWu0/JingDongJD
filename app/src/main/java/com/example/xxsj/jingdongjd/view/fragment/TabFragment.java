package com.example.xxsj.jingdongjd.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayout;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayoutDirection;
import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.app.MyApplication;
import com.example.xxsj.jingdongjd.bean.TuijianBean;
import com.example.xxsj.jingdongjd.view.adapter.MyRecyclerViewAdapter;
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

public class TabFragment extends Fragment{

    private View view;
    private RecyclerView recyclerView;
    private SwipyRefreshLayout recyclerRefresh;
    private Handler handler=null;
    int page=1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initNetWorkData();
        handler=new Handler();

        //设置颜色
        recyclerRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, android.R.color.holo_red_dark);
        //设置是否支持刷新和加载更多
        recyclerRefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);

        recyclerRefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerRefresh.setRefreshing(false);
                        page++;
                        initNetWorkData();
                        Toast.makeText(getActivity(),"加载成功", Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }

            @Override
            public void onLoad(int index) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        initNetWorkData();
                        recyclerRefresh.setRefreshing(false);
                        Toast.makeText(getActivity(),"加载成功", Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }
        });
    }
    private void initView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerRefresh = (SwipyRefreshLayout) view.findViewById(R.id.recyclerRefresh);
    }
    private void initNetWorkData() {
        OkHttpClient okHttpClient = MyApplication.okHttpClient();
        Request request = new Request.Builder().url("http://apiv3.yangkeduo.com/v5/newlist?page="+page+"&size=20").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        TuijianBean tuijianBean = gson.fromJson(json, TuijianBean.class);
                        List<TuijianBean.GoodsListBean> goods_list = tuijianBean.getGoods_list();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),goods_list);
                        recyclerView.setAdapter(myRecyclerViewAdapter);
                    }
                });


            }
        });
    }
}
