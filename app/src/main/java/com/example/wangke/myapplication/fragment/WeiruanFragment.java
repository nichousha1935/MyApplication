package com.example.wangke.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeiruanFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        TextView textView = view.findViewById(R.id.tv_content);
        textView.setOnClickListener(this);
        textView.setText("微软");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ToastUtil.showToast(getContext(),getTime(date));
                    }
                }).setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                 .build();
                pvTime.show();
                break;
            default:
                break;
        }
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
