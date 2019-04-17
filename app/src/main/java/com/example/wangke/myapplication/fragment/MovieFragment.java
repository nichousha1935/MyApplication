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
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.scatter.SquareShapeRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * 文件描述:
 * https://blog.csdn.net/wqbs369/article/details/79868131
 * @author Administrator
 * @date 2019/1/7
 */
public class MovieFragment extends Fragment implements OnChartValueSelectedListener {
    private ScatterChart mScatterChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, null);
        //散点图
        mScatterChart = view.findViewById(R.id.mScatterChart);
        mScatterChart.getDescription().setEnabled(false);
        mScatterChart.setOnChartValueSelectedListener(this);
        mScatterChart.setBackgroundColor(Color.parseColor("#D9D9D9"));

        mScatterChart.setDrawGridBackground(false);
        mScatterChart.setTouchEnabled(true);
        mScatterChart.setMaxHighlightDistance(10f);

        // 支持缩放和拖动
        mScatterChart.setDragEnabled(true);
        mScatterChart.setScaleEnabled(true);

        mScatterChart.setMaxVisibleValueCount(10);
        mScatterChart.setPinchZoom(true);

        //设置比例图
        Legend l = mScatterChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXOffset(5f);

        YAxis yl = mScatterChart.getAxisLeft();
        yl.setAxisMinimum(5f);
        yl.setAxisMaximum(15f);
        LimitLine yLimitLineOne = new LimitLine(10f,"及格线");
        yLimitLineOne.setLineColor(Color.RED);
        yLimitLineOne.setTextColor(Color.RED);
        yLimitLineOne.setLineWidth(4f);

        yLimitLineOne.enableDashedLine(20f,2f,2f);

        LimitLine yLimitLineTwo= new LimitLine(6f,"及格线");
        yLimitLineTwo.setLineColor(Color.RED);
        yLimitLineTwo.setTextColor(Color.RED);
        yl.addLimitLine(yLimitLineOne);
        yl.addLimitLine(yLimitLineTwo);

        mScatterChart.getAxisRight().setEnabled(false);

        XAxis xl = mScatterChart.getXAxis();
        xl.setAxisMinimum(0f);
        xl.setAxisMaximum(20f);
        xl.setDrawGridLines(false);
        // 设置x轴的LimitLine，index是从?开始的
        LimitLine xLimitLine = new LimitLine(4f,"及格线");
        xLimitLine.setLineColor(Color.GREEN);
        xLimitLine.setTextColor(Color.GREEN);
        xl.addLimitLine(xLimitLine);

        setData();

        return view;
    }

    //设置数据
    private void setData() {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        ArrayList<Entry> yVals2 = new ArrayList<>();
        ArrayList<Entry> yVals3 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            float val = (float) (Math.random() * 10 + 3);
            yVals1.add(new Entry(i, val));
        }

        for (int i = 0; i < 10; i++) {
            float val = (float) (Math.random() * 10 + 3);
            yVals2.add(new Entry(i + 0.33f, val));
        }

        for (int i = 0; i < 10; i++) {
            float val = (float) (Math.random() * 10 + 3);
            yVals3.add(new Entry(i + 0.66f, val));
        }

        //创建一个数据集,并给它一个类型
        ScatterDataSet set1 = new ScatterDataSet(yVals1, "优秀");
        set1.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        //设置颜色
        set1.setColor(Color.parseColor("#000000"));
        ScatterDataSet set2 = new ScatterDataSet(yVals2, "及格");
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set2.setScatterShapeHoleColor(Color.parseColor("#FF3499"));
        set2.setScatterShapeHoleRadius(3f);
        set2.setColor(Color.parseColor("#FF3499"));
        ScatterDataSet set3 = new ScatterDataSet(yVals3, "不及格");
        set3.setShapeRenderer(new SquareShapeRenderer());
        set3.setColor(ColorTemplate.COLORFUL_COLORS[2]);

        set1.setScatterShapeSize(8f);
        set2.setScatterShapeSize(8f);
        set3.setScatterShapeSize(8f);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);

        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);

        mScatterChart.setData(data);
        mScatterChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
