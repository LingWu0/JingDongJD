package com.example.xxsj.jingdongjd.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayout;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayoutDirection;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.app.MyApplication;
import com.example.xxsj.jingdongjd.bean.TuijianBean;
import com.example.xxsj.jingdongjd.view.adapter.MyHomeRecyclerviewAdapter;
import com.example.xxsj.jingdongjd.view.adapter.MyHomeViewPagerAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
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

public class HomeFragment extends Fragment {

    private View view;
    private SliderLayout sliderLayout;
    private Handler handler = null;
    private RadioGroup radioGroup;
    private int page = 1;
    private ArrayList<Integer> imageViews;
    private ArrayList<Fragment> views;
    //  private String[] images;
    private MyHomeRecyclerviewAdapter myHomeRecyclerviewAdapter;
    private MyHomeViewPagerAdapter myHomeViewPagerAdapter;
    private ViewPager homeViewpager;
    private RecyclerView recycler;
    private boolean flag;
    private SwipyRefreshLayout swipyRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        ViewFlipper vf = (ViewFlipper)view.findViewById(R.id.vf);

        vf.addView(View.inflate(getActivity(), R.layout.view_advertisement01, null));
        vf.addView(View.inflate(getActivity(), R.layout.view_advertisement02, null));
        vf.addView(View.inflate(getActivity(), R.layout.view_advertisement03, null));

        handler = new Handler();
        Sliderer();
        return view;
    }

    //图片轮播
    private void Sliderer() {
        //一
        TextSliderView textSliderView = new TextSliderView(getContext());
        textSliderView
                .description("女鞋")
                .image("https://img1.360buyimg.com/da/jfs/t8431/284/477854473/99917/47e31702/59a91e71N1c4a1448.jpg");

        //二
        TextSliderView textSliderView2 = new TextSliderView(getContext());

        textSliderView2.description("晒单评价")
                .image("https://img11.360buyimg.com/babel/jfs/t8899/346/1243045779/95475/1dac304c/59b626bbNeaf14b36.jpg");

        //三
        TextSliderView textSliderView3 = new TextSliderView(getContext());

        textSliderView3.description("小米")
                .image("https://img10.360buyimg.com/babel/jfs/t8320/150/1227766208/84920/c9c11e2/59b6674eNfa4d8466.jpg");
//四
        TextSliderView textSliderView4 = new TextSliderView(getContext());

        textSliderView4.description("家居")
                .image("https://img1.360buyimg.com/da/jfs/t7324/354/2665000409/94483/41814fb/59b2551eNb4f04183.jpg");


        //添加
        sliderLayout.addSlider(textSliderView);
        sliderLayout.addSlider(textSliderView2);
        sliderLayout.addSlider(textSliderView3);
        sliderLayout.addSlider(textSliderView4);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        initData();
        myHomeViewPagerAdapter = new MyHomeViewPagerAdapter(getActivity().getSupportFragmentManager(), views);
        homeViewpager.setAdapter(myHomeViewPagerAdapter);
        homeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //获取网络数据的方法
        initNetWorkData();
        swipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        page++;
                        initNetWorkData();
                        swipyRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }

            @Override
            public void onLoad(int index) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        initNetWorkData();
                        swipyRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });

    }

    ScrollView scrollView;

    //找到控件
    private void initview() {
        recycler = (RecyclerView) view.findViewById(R.id.home_recyclerview);
        swipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.home_refresh);
        scrollView = (ScrollView) view.findViewById(R.id.pull_refresh_scroll);
        homeViewpager = (ViewPager) view.findViewById(R.id.homeViewpager);
        radioGroup = (RadioGroup) view.findViewById(R.id.homeViewpager_radioGroup);
       /* homeBanner1 = (Banner) view.findViewById(R.id.home_banner);
        //设置homeBanner1样式
        homeBanner1.setBannerStyle(Banner.CIRCLE_INDICATOR);
        homeBanner1.setIndicatorGravity(Banner.CENTER);
        homeBanner1.setDelayTime(3000);//设置轮播间隔时间
        homeBanner1.setImages(images);//可以选择设置图片网址，或者资源文件，默认用Glide加载*/
        RadioButton radioButton2 = (RadioButton) radioGroup.getChildAt(0);
        radioButton2.setChecked(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.smoothScrollTo(0, 0);
    }

    /* public void scrollScrollView() {
         if (scrollView != null) {
             scrollView.smoothScrollTo(0, 0);
         }

     }*/
    //获取数据
    private void initData() {
        imageViews = new ArrayList<>();
        imageViews.add(R.drawable.afv);
        imageViews.add(R.drawable.as5);
        imageViews.add(R.drawable.guidepage);
        views = new ArrayList<>();
        HomeViewpager1Fragment homeViewpager1Fragment = new HomeViewpager1Fragment();
        HomeViewpager2Fragment homeViewpager2Fragment = new HomeViewpager2Fragment();
        views.add(homeViewpager1Fragment);
        views.add(homeViewpager2Fragment);
       /* images = new String[]{"https://img14.360buyimg.com/da/jfs/t8380/119/300584693/62704/5d07a5d/59a4bf60N67e35778.jpg",
                "https://img1.360buyimg.com/da/jfs/t7324/354/2665000409/94483/41814fb/59b2551eNb4f04183.jpg",
                "https://img1.360buyimg.com/da/jfs/t8431/284/477854473/99917/47e31702/59a91e71N1c4a1448.jpg",
                "https://img12.360buyimg.com/babel/jfs/t7546/303/2895014485/114582/dc04f9e8/59b65103N2085ffa9.jpg",
                "https://img20.360buyimg.com/da/jfs/t9163/71/400427467/41277/8dbf5f28/59a7a48aN7a42d3fe.jpg",
                "https://img14.360buyimg.com/babel/jfs/t8638/200/1234149431/119735/6c3312d1/59b62500Nbdb53d03.jpg",
                "https://img10.360buyimg.com/babel/jfs/t8320/150/1227766208/84920/c9c11e2/59b6674eNfa4d8466.jpg",
                "https://img11.360buyimg.com/babel/jfs/t8899/346/1243045779/95475/1dac304c/59b626bbNeaf14b36.jpg"};*/
    }

    private void initNetWorkData() {
        OkHttpClient okHttpClient = MyApplication.okHttpClient();
        Request request = new Request.Builder().url("http://apiv3.yangkeduo.com/v5/newlist?page=" + page + "&size=20").build();
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
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recycler.setLayoutManager(gridLayoutManager);
                        if (myHomeRecyclerviewAdapter == null) {
                            myHomeRecyclerviewAdapter = new MyHomeRecyclerviewAdapter(getActivity(), goods_list);
                            recycler.setAdapter(myHomeRecyclerviewAdapter);
                        } else {
                            myHomeRecyclerviewAdapter.loadMore(goods_list, flag);
                            myHomeRecyclerviewAdapter.notifyDataSetChanged();
                        }
                    }
                });


            }
        });
    }
}
