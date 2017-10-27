package com.example.xxsj.jingdongjd.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.bean.ClassifyGridViewBean;

import java.util.List;


/**
 * Created by Administrator on 2017/9/9.
 */

public class MyGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<ClassifyGridViewBean.DatasBean.ClassListBean> classgridview_list;

    public MyGridViewAdapter(Context context, List<ClassifyGridViewBean.DatasBean.ClassListBean> classgridview_list) {
        this.context = context;
        this.classgridview_list = classgridview_list;
    }

    @Override
    public int getCount() {
        return classgridview_list.size();
    }

    @Override
    public Object getItem(int position) {
        return classgridview_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.gridview_items, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.gridview_item_text);
        ImageView image = (ImageView) convertView.findViewById(R.id.gridview_item_image);
        text.setText(classgridview_list.get(position).getGc_name());
        image.setImageResource(R.drawable.nanzhuang);
        return convertView;
    }
}
