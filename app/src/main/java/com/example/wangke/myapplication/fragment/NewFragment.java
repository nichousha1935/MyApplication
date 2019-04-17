package com.example.wangke.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.utils.Tool;
import com.example.wangke.myapplication.views.MeiZuMonthView;
import com.example.wangke.myapplication.views.MeizuWeekView;
import com.example.wangke.myapplication.views.SimpleMonthView;
import com.example.wangke.myapplication.views.SimpleWeekView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/5
 */
public class NewFragment extends Fragment implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, View.OnClickListener {
    private CalendarView mCalendarView;
    private TextView mTextMonthDay, mTextYear, mTextLunar, mTextCurrentDay;
    private RelativeLayout mRelativeTool;
    private FrameLayout mFrameLayout;
    private int mYear;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mFrameLayout=view.findViewById(R.id.fl_current);
        mTextMonthDay = view.findViewById(R.id.tv_month_day);
        mTextYear = view.findViewById(R.id.tv_year);
        mTextLunar = view.findViewById(R.id.tv_lunar);
        mRelativeTool = view.findViewById(R.id.rl_tool);
        mCalendarView = view.findViewById(R.id.calendarView);
        mTextCurrentDay = view.findViewById(R.id.tv_current_day);
        mCalendarView.setMonthView(SimpleMonthView.class);
        mCalendarView.setWeekView(SimpleWeekView.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public void init(){
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开日历年月份快速选择
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
       // mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "迟到").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "迟到"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "早退").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "早退"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "迟到").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "迟到"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "迟到").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "迟到"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        //标记哪些日期有早退件
        mCalendarView.setSchemeDate(map);

}
    @Override
    public void onClick(View v) {
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        if (Tool.isEmpty(calendar.getScheme())){
            ToastUtil.showToast(getContext(),"考勤正常");
        }
        else {
            ToastUtil.showToast(getContext(),calendar.getScheme());
        }
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
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
