package com.example.wangke.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.utils.Tool;
import com.example.wangke.myapplication.views.SimpleMonthView;
import com.example.wangke.myapplication.views.SimpleWeekView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/5
 */
public class NewFragment extends Fragment implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, View.OnClickListener {
    private CalendarView mCalendarView;
    private TextView mTextYear, mTextMonth;
    private int mYear, mMonth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mTextYear = view.findViewById(R.id.calendar_year);
        mTextMonth = view.findViewById(R.id.calendar_month);
        mCalendarView = view.findViewById(R.id.calendarView);
        mCalendarView.setMonthViewScrollable(false);
        mCalendarView.setMonthView(SimpleMonthView.class);
        mCalendarView.setWeekView(SimpleWeekView.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public void init() {
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setOnClickListener(this);
        mTextMonth.setOnClickListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mTextMonth.setText(String.valueOf(mCalendarView.getCurMonth()));
        mYear = mCalendarView.getCurYear();
        mMonth = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(mYear, mMonth, 3, 0xFF40db25, "迟到").toString(),
                getSchemeCalendar(mYear, mMonth, 3, 0xFF40db25, "迟到"));
        map.put(getSchemeCalendar(mYear, mMonth, 6, 0xFFe69138, "早退").toString(),
                getSchemeCalendar(mYear, mMonth, 6, 0xFFe69138, "早退"));
        map.put(getSchemeCalendar(mYear, mMonth, 15, 0xFFaacc44, "迟到").toString(),
                getSchemeCalendar(mYear, mMonth, 15, 0xFFaacc44, "迟到"));
        map.put(getSchemeCalendar(mYear, mMonth, 25, 0xFF13acf0, "迟到").toString(),
                getSchemeCalendar(mYear, mMonth, 25, 0xFF13acf0, "迟到"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        //标记哪些日期有早退件
        mCalendarView.setSchemeDate(map);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendar_year:
                List<String> optionsYearItems=new ArrayList<>();
                for (int i=2000;i<2100;i++){
                    optionsYearItems.add(String.valueOf(i));
                }
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        mTextYear.setText(optionsYearItems.get(options1));
                        mCalendarView.scrollToCalendar(Integer.parseInt(String.valueOf(mTextYear.getText())),Integer.parseInt(String.valueOf(mTextMonth.getText())),1);
                    }
                }).setTitleText("时间").build();
                pvOptions.setPicker(optionsYearItems);
                pvOptions.show();
                break;
            case R.id.calendar_month:
                List<String> optionsMonthItems=new ArrayList<>();
                for (int i=1;i<13;i++){
                    optionsMonthItems.add(String.valueOf(i));
                }
                OptionsPickerView pvOptions1 = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        mTextMonth.setText(optionsMonthItems.get(options1));
                        mCalendarView.scrollToCalendar(Integer.parseInt(String.valueOf(mTextYear.getText())),Integer.parseInt(String.valueOf(mTextMonth.getText())),1);
                    }
                }).build();
                pvOptions1.setPicker(optionsMonthItems);
                pvOptions1.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        if (isClick){
            if (Tool.isEmpty(calendar.getScheme())) {
                ToastUtil.showToast(getContext(), "考勤正常");
            } else {
                ToastUtil.showToast(getContext(), calendar.getScheme());
            }
        }
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextMonth.setText(String.valueOf(calendar.getMonth()));
    }

    @Override
    public void onYearChange(int year) {
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "迟到");
        calendar.addScheme(0xFF008800, "早退");
        return calendar;
    }
}
