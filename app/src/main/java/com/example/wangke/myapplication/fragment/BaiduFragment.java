package com.example.wangke.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.okhttp.CallBackUtil;
import com.example.wangke.myapplication.okhttp.OkhttpUtil;

import java.util.HashMap;
import java.util.Map;

import bean.ListCarInfo;
import okhttp3.Call;

public class BaiduFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        TextView textView = view.findViewById(R.id.tv_content);
        textView.setText("百度");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.2.232:8081/appLogin";
                Map<String, String> map = new HashMap<>();
                map.put("password", "12345");
                map.put("userName", "csf1");

                Map<String, String> heaherMap = new HashMap<>();
                heaherMap.put("content-type", "application/json");
                OkhttpUtil.okHttpPost(url, map, heaherMap, new CallBackUtil.CallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }


                    @Override
                    public void onResponse(String response) {

//                    ListCarInfo listCarInfo= JSON.parseObject(response, ListCarInfo.class);
//                    listCarInfo.getList().get(0).getPlateNumber();
                    }
                });

            }
        });
        return view;
    }
}
