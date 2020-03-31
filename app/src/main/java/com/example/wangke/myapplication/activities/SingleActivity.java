package com.example.wangke.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.adapter.CommonAdapter;
import com.example.wangke.myapplication.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SingleActivity extends BaseActivity {
    private ListView listView;
    private CommonAdapter<String> commonAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setSteepStatusBar(true);
        setScreenRoate(false);
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_single);
        getToolBar().setTitle("单选页面");
        getToolBar().setRightVislble(false);
    }

    @Override
    public void initView() {
        listView = findViewById(R.id.listView);
    }

    @Override
    public void initBind() {
        list.add("王一明");
        list.add("王二明");
        list.add("王三明");
        list.add("王四明");
        list.add("王五明");
        list.add("王六明");
        list.add("王七明");
        list.add("王八明");
        list.add("王九明");
        list.add("王十明");
    }

    @Override
    public void initData() {
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_single, list) {
            @Override
            public void convert(ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.tv_name, s);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
