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
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.DateUtils;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.utils.Tool;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeiruanFragment extends Fragment implements View.OnClickListener {
    private TimePickerView pvTime;
    private Calendar selectedDateStart = Calendar.getInstance();
    private Calendar selectedDateEnd = Calendar.getInstance();
    private TextView tv_start_time, tv_end_time, tv_duration;
    private long startTime, endTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        selectedDateEnd.set(2019,5,12,0,0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        tv_start_time = view.findViewById(R.id.tv_start_time);
        tv_start_time.setOnClickListener(this::onClick);
        tv_end_time = view.findViewById(R.id.tv_end_time);
        tv_end_time.setOnClickListener(this::onClick);
        tv_duration = view.findViewById(R.id.tv_duration);
        TextView textView = view.findViewById(R.id.tv_content);
        textView.setOnClickListener(this);
        textView.setText("微软");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:

                break;
            case R.id.tv_start_time:
                //时间选择器
                pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startTime = DateUtils.convertToLong(getTime(date), "yyyy-MM-dd HH:mm:ss");
                        if (startTime - endTime > 0 && endTime != 0) {
                            ToastUtil.showToast(getContext(), "结束时间必须小于开始时间");
                        } else {
                            if (endTime!=0){
                                BigDecimal bigDecimal = BigDecimal.valueOf((endTime - startTime) * 1.0 / DateUtils.ONE_DAY).setScale(2, BigDecimal.ROUND_HALF_UP);
                                tv_duration.setText(bigDecimal.toString());
                            }
                            tv_start_time.setText(getTime(date));
                            selectedDateEnd.setTime(date);
                            pvTime.dismiss();
                        }
                    }
                }).setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                        .setLayoutRes(R.layout.view_timepicker, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                TextView textViewOk = v.findViewById(R.id.tv_finish);
                                textViewOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pvTime.returnData();
                                    }
                                });
                            }
                        }).setDate(selectedDateStart)
                        .build();
                pvTime.show();
                break;
            case R.id.tv_end_time:
                if (Tool.isEmpty(tv_start_time.getText())) {
                    ToastUtil.showToast(getContext(), "请先选择开始时间");
                } else {
                    //时间选择器
                    pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {

                        @Override
                        public void onTimeSelect(Date date, View v) {
                            endTime = DateUtils.convertToLong(getTime(date), "yyyy-MM-dd HH:mm:ss");
                            if (endTime - startTime <= 0) {
                                ToastUtil.showToast(getContext(), "结束时间必须大于开始时间");
                            } else {
                                tv_end_time.setText(getTime(date));
                                BigDecimal bigDecimal = BigDecimal.valueOf((endTime - startTime) * 1.0 / DateUtils.ONE_DAY).setScale(2, BigDecimal.ROUND_HALF_UP);
                                tv_duration.setText(bigDecimal.toString());
                                pvTime.dismiss();
                            }
                        }
                    }).setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                            .setLayoutRes(R.layout.view_timepicker, new CustomListener() {
                                @Override
                                public void customLayout(View v) {
                                    TextView textViewOk = v.findViewById(R.id.tv_finish);
                                    textViewOk.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pvTime.returnData();
                                        }
                                    });
                                }
                            }).setDate(selectedDateEnd)
                            .build();
                    pvTime.show();
                }

                break;
            default:
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        SimpleDateFormat formatWeek = new SimpleDateFormat("E");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH");
        SimpleDateFormat formatMin = new SimpleDateFormat("mm");
        SimpleDateFormat formatSecond = new SimpleDateFormat("ss");
        return format.format(date);
    }
}
