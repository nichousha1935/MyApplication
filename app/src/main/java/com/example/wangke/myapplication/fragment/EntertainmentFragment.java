package com.example.wangke.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangke.myapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/7
 */
public class EntertainmentFragment extends Fragment implements OnChartValueSelectedListener {
    private PieChart mPieChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entertainment, null);
        //实现饼状图
        mPieChart = view.findViewById(R.id.mPieChart);
        //设置显示成比例
        mPieChart.setUsePercentValues(true);
        //设置隐藏饼图上文字，只显示百分比
        mPieChart.setDrawSliceText(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText("鼎集智能");
        //是否绘制饼状图中间的圆
        mPieChart.setDrawHoleEnabled(true);
        //饼状图中间圆绘制颜色
        mPieChart.setHoleColor(Color.WHITE);
        //设置圆环的颜色
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        //设置内圆半径
        mPieChart.setHoleRadius(50f);
        //设置半透明圈
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);
        //初始旋转角度
        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        //设置旋转的时候点中的tab是否高亮
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);
        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(100, "优秀"));
        entries.add(new PieEntry(200, "满分"));
        entries.add(new PieEntry(300, "及格"));
        entries.add(new PieEntry(400, "不及格"));


        //设置数据
        setData(entries);
        mPieChart.animateY(1400, Easing.EaseInOutQuad);
        //设置比例图
        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        //设置比例块换行...
        //  l.setWordWrapEnabled(true);
        //设置禁用比例块
       // l.setEnabled(false);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
        return view;
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "三年级一班");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
         //数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        //设置连接线的颜色
        dataSet.setValueLineColor(Color.YELLOW);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(mPieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}