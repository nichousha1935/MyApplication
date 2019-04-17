package com.example.wangke.myapplication.fragment;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangke.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述:
 *
 * @author wangke
 * @date 2019/1/7
 */
public class SocietyFragment extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {
    private LineChart lineChart;
    private LineDataSet set1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_society, null);
        lineChart = view.findViewById(R.id.chart);
        //设置手势滑动事件
        lineChart.setOnChartGestureListener(this);
        //设置数值选择监听
        lineChart.setOnChartValueSelectedListener(this);
        //后台绘制
        lineChart.setDrawGridBackground(false);
        //设置描述文本
        lineChart.getDescription().setEnabled(false);
        //设置支持触控手势
        lineChart.setTouchEnabled(true);
        //设置缩放
        lineChart.setDragEnabled(true);
        //设置推动
        lineChart.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        lineChart.setPinchZoom(true);
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(5, 50));
        values.add(new Entry(10, 66));
        values.add(new Entry(15, 120));
        values.add(new Entry(20, 30));
        values.add(new Entry(35, 10));
        values.add(new Entry(40, 110));
        values.add(new Entry(45, 30));
        values.add(new Entry(50, 160));
        values.add(new Entry(100, 30));

        //设置数据
        setData(values);
        //默认动画
        lineChart.animateX(2500);
        //刷新
        lineChart.invalidate();
        // 得到这个文字
        Legend l = lineChart.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);
        List<ILineDataSet> setsFilled = lineChart.getData().getDataSets();
//        for (ILineDataSet iSet : setsFilled) {
//            LineDataSet set = (LineDataSet) iSet;
//            if (set.isDrawFilledEnabled())
//                set.setDrawFilled(false);
//            else
//                set.setDrawFilled(true);
//        }
        lineChart.invalidate();
        return view;
    }

    private void setData(ArrayList<Entry> values) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "年度总结报告");

            // 在这里设置线
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Build.VERSION.SDK_INT >= 18) {
                // 填充背景只支持18以上
                //Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                //set1.setFillDrawable(drawable);
                set1.setFillColor(Color.YELLOW);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);

            //谁知数据
            lineChart.setData(data);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
