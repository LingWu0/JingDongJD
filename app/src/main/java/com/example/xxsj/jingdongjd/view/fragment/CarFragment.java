package com.example.xxsj.jingdongjd.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.bean.GoodsBean;
import com.example.xxsj.jingdongjd.bean.StoreBean;
import com.example.xxsj.jingdongjd.view.adapter.MyBaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的用途:仿京东商城
 * 作者:郝兵丽
 * 日期:2017/9/19
 */
public class CarFragment extends Fragment {

    //定义父列表项List数据集合
    List<Map<String, Object>> parentMapList = new ArrayList<Map<String, Object>>();
    //定义子列表项List数据集合
    List<List<Map<String, Object>>> childMapList_list = new ArrayList<List<Map<String, Object>>>();

    MyBaseExpandableListAdapter myBaseExpandableListAdapter;
    CheckBox id_cb_select_all;
    LinearLayout id_ll_normal_all_state;
    LinearLayout id_ll_editing_all_state;
    ExpandableListView expandableListView;
    RelativeLayout id_rl_cart_is_empty;
    RelativeLayout id_rl_foot;

    TextView id_tv_edit_all;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_car, null, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();

        initCartData();
        expandableListView = (ExpandableListView) view.findViewById(R.id.id_elv_listview);

        myBaseExpandableListAdapter = new MyBaseExpandableListAdapter(getContext(), parentMapList, childMapList_list);
        expandableListView.setAdapter(myBaseExpandableListAdapter);
        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "click：" + position, Toast.LENGTH_SHORT).show();

            }
        });


        for (int i = 0; i < parentMapList.size(); i++) {
            expandableListView.expandGroup(i);
        }

        id_ll_normal_all_state = (LinearLayout) view.findViewById(R.id.id_ll_normal_all_state);
        id_ll_editing_all_state = (LinearLayout) view.findViewById(R.id.id_ll_editing_all_state);
        id_rl_cart_is_empty = (RelativeLayout) view.findViewById(R.id.id_rl_cart_is_empty);
        TextView id_tv_save_star_all = (TextView) view.findViewById(R.id.id_tv_save_star_all);
        id_tv_save_star_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "收藏多选商品", Toast.LENGTH_SHORT).show();
            }
        });
        TextView id_tv_delete_all = (TextView) view.findViewById(R.id.id_tv_delete_all);
        id_tv_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBaseExpandableListAdapter.removeGoods();
            }
        });

        id_tv_edit_all = (TextView) view.findViewById(R.id.id_tv_edit_all);

        id_tv_edit_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    if (MyBaseExpandableListAdapter.EDITING.equals(tv.getText())) {
                        myBaseExpandableListAdapter.setupEditingAll(true);
                        tv.setText(MyBaseExpandableListAdapter.FINISH_EDITING);
                        changeFootShowDeleteView(true);//这边类似的功能 后期待使用观察者模式
                    } else {
                        myBaseExpandableListAdapter.setupEditingAll(false);
                        tv.setText(MyBaseExpandableListAdapter.EDITING);
                        changeFootShowDeleteView(false);//这边类似的功能 后期待使用观察者模式
                    }

                }
            }
        });

        id_cb_select_all = (CheckBox) view.findViewById(R.id.id_cb_select_all);
        id_cb_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) v;
                    myBaseExpandableListAdapter.setupAllChecked(checkBox.isChecked());
                }
            }
        });

        final TextView id_tv_totalPrice = (TextView) view.findViewById(R.id.id_tv_totalPrice);

        final TextView id_tv_totalCount_jiesuan = (TextView) view.findViewById(R.id.id_tv_totalCount_jiesuan);
        id_tv_totalCount_jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "结算", Toast.LENGTH_SHORT).show();
            }
        });
        myBaseExpandableListAdapter.setOnGoodsCheckedChangeListener(new MyBaseExpandableListAdapter.OnGoodsCheckedChangeListener() {
            @Override
            public void onGoodsCheckedChange(int totalCount, double totalPrice) {
                id_tv_totalPrice.setText(String.format(getString(R.string.total), totalPrice));
                id_tv_totalCount_jiesuan.setText(String.format(getString(R.string.jiesuan), totalCount));
            }
        });

        myBaseExpandableListAdapter.setOnAllCheckedBoxNeedChangeListener(new MyBaseExpandableListAdapter.OnAllCheckedBoxNeedChangeListener() {
            @Override
            public void onCheckedBoxNeedChange(boolean allParentIsChecked) {
                id_cb_select_all.setChecked(allParentIsChecked);
            }
        });

        myBaseExpandableListAdapter.setOnEditingTvChangeListener(new MyBaseExpandableListAdapter.OnEditingTvChangeListener() {
            @Override
            public void onEditingTvChange(boolean allIsEditing) {

                changeFootShowDeleteView(allIsEditing);//这边类似的功能 后期待使用观察者模式

            }
        });

        myBaseExpandableListAdapter.setOnCheckHasGoodsListener(new MyBaseExpandableListAdapter.OnCheckHasGoodsListener() {
            @Override
            public void onCheckHasGoods(boolean isHasGoods) {
                setupViewsShow(isHasGoods);
            }
        });


        /**====include进来方式可能会导致view覆盖listview的最后一个item 解决*/
        //在onCreate方法中一般没办法直接调用view.getHeight方法来获取到控件的高度
        id_rl_foot = (RelativeLayout) view.findViewById(R.id.id_rl_foot);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        id_rl_foot.measure(w, h);
        int r_width = id_rl_foot.getMeasuredWidth();
        int r_height = id_rl_foot.getMeasuredHeight();
        Log.i("MeasureSpec", "MeasureSpec r_width = " + r_width);
        Log.i("MeasureSpec", "MeasureSpec r_height = " + r_height);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int top = dp2px(getContext(), 48);
        lp.setMargins(0, top, 0, r_height);//48

        expandableListView.setLayoutParams(lp);

        if (parentMapList != null && parentMapList.size() > 0) {
            setupViewsShow(true);
        } else {
            setupViewsShow(false);
        }
    }

    private void setupViewsShow(boolean isHasGoods) {
        if (isHasGoods) {
            expandableListView.setVisibility(View.VISIBLE);
            id_rl_cart_is_empty.setVisibility(View.GONE);
            id_rl_foot.setVisibility(View.VISIBLE);
            id_tv_edit_all.setVisibility(View.VISIBLE);
        } else {
            expandableListView.setVisibility(View.GONE);
            id_rl_cart_is_empty.setVisibility(View.VISIBLE);
            id_rl_foot.setVisibility(View.GONE);
            id_tv_edit_all.setVisibility(View.GONE);
        }
    }

    public void changeFootShowDeleteView(boolean showDeleteView) {

        if (showDeleteView) {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.FINISH_EDITING);

            id_ll_normal_all_state.setVisibility(View.INVISIBLE);
            id_ll_editing_all_state.setVisibility(View.VISIBLE);
        } else {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.EDITING);

            id_ll_normal_all_state.setVisibility(View.VISIBLE);
            id_ll_editing_all_state.setVisibility(View.INVISIBLE);
        }
    }

    public int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //购物车数据
    private void initCartData() {
        for (int i = 0; i < 4; i++) {
            String store = "旗舰店";
            if (i % 2 == 0) {
                store = "专营店";
            }
            //提供父列表的数据
            Map<String, Object> parentMap = new HashMap<String, Object>();

            parentMap.put("parentName", new StoreBean("" + i, store + i, false, false));
            parentMapList.add(parentMap);
            //提供当前父列的子列数据
            List<Map<String, Object>> childMapList = new ArrayList<Map<String, Object>>();
            for (int j = 0; j < 3; j++) {
                Map<String, Object> childMap = new HashMap<String, Object>();

                GoodsBean goodsBean = new GoodsBean(i + "_" + j, store + i + "下的商品" + j, "url", "均码，白色", 150, 99, 1, GoodsBean.STATUS_VALID, false, false);
                childMap.put("childName", goodsBean);
                childMapList.add(childMap);
            }
            childMapList_list.add(childMapList);
        }
    }

}
