package com.example.wangke.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.adapter.CommonAdapter;
import com.example.wangke.myapplication.adapter.ViewHolder;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.views.MyToolBar;

import java.util.ArrayList;
import java.util.List;

import bean.PersonSelectBean;

public class MultiActivity extends BaseActivity {
    private ListView listView;
    private CommonAdapter<PersonSelectBean> commonAdapter;
    private List<PersonSelectBean> list = new ArrayList<>();
    private List<String> personList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_single);
        getToolBar().setTitle("多选页面");
        getToolBar().setRightVislble(false);
        getToolBar().setRightText("提交");
        getToolBar().setToolBarRightOnClickListener(new MyToolBar.ToolBarRightOnClickListener() {
            @Override
            public void setToolBarRightOnClick() {
                // ToastUtil.showToast(MultiActivity.this, JSON.toJSONString(list));
                for (PersonSelectBean personSelectBean : list) {
                    if (personSelectBean.isSelect()) {
                        personList.add(personSelectBean.getName());
                    }
                }
                 ToastUtil.showToast(MultiActivity.this, JSON.toJSONString(personList));
            }
        });
    }

    @Override
    public void initView() {
        listView = findViewById(R.id.listView);
    }

    @Override
    public void initBind() {
        for (int i = 0; i < 10; i++) {
            PersonSelectBean personSelectBean = new PersonSelectBean();
            personSelectBean.setName("王小明    " + i);
            personSelectBean.setSelect(false);
            list.add(personSelectBean);
        }

    }

    @Override
    public void initData() {
        commonAdapter = new CommonAdapter<PersonSelectBean>(this, R.layout.item_multi, list) {
            @Override
            public void convert(ViewHolder viewHolder, PersonSelectBean s) {
                viewHolder.setText(R.id.tv_name, s.getName());
                viewHolder.setImage(R.id.img_res, s.isSelect() ? R.mipmap.selected : R.mipmap.no_selected);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s.setSelect(s.isSelect() ? false : true);
                        commonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        listView.setAdapter(commonAdapter);
    }

    @Override
    public void widgetClick(View v) {

    }
}
