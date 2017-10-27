package com.example.xxsj.jingdongjd.view.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.bean.TuijianBean;

import java.util.List;


/**
 * Created by Administrator on 2017/9/7.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private FragmentActivity activity;
    private List<TuijianBean.GoodsListBean> goods_list;
    public MyRecyclerViewAdapter(FragmentActivity activity, List<TuijianBean.GoodsListBean> goods_list) {
        this.activity = activity;
        this.goods_list=goods_list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item1, parent, false);
                holder = new MyViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2, parent, false);
                holder = new MyViewHolder2(view);
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                MyViewHolder holder1= (MyViewHolder) holder;
                holder1.title1.setText(goods_list.get(position).getGoods_name());
                Glide.with(activity).load(goods_list.get(position).getThumb_url()).into(holder1.image1);
                break;
            case 1:
                MyViewHolder2 holder2= (MyViewHolder2) holder;
                holder2.title2.setText(goods_list.get(position).getGoods_name());
                Glide.with(activity).load(goods_list.get(position).getThumb_url()).into(holder2.image2);
                break;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView image1;
        private TextView title1;

        public MyViewHolder(View itemView) {
            super(itemView);
            image1 = (ImageView) itemView.findViewById(R.id.recyclerView_image1);
            title1 = (TextView) itemView.findViewById(R.id.recyclerView_title1);
        }


    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private ImageView image2;
        private  TextView title2;

        public MyViewHolder2(View itemView) {
            super(itemView);
            image2 = (ImageView) itemView.findViewById(R.id.recyclerView_image2);
            title2 = (TextView) itemView.findViewById(R.id.recyclerView_title2);
        }

    }

    @Override
    public int getItemCount() {
        return goods_list.size();
    }
}
