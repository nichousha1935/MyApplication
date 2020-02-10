package com.example.wangke.myapplication.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.adapter.CommonAdapter;
import com.example.wangke.myapplication.adapter.ViewHolder;
import com.example.wangke.myapplication.utils.DeviceUtils;

import java.util.List;

public class ScreenPopupWindows extends PopupWindow {
    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    private ListView lt_screen;
    private CommonAdapter<String> commonAdapter;
    private ClickListener clickListener;
    private int select;

    public ScreenPopupWindows(Context context, List<String> list,int select, ClickListener clickListener) {
        this.context = context;
        this.select=select;
        this.clickListener = clickListener;
        this.list = list;
        init();
    }

    public void init() {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_screen_pop, null);
        setContentView(view);
        setWidth(DeviceUtils.getScreenWidth(context));
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        lt_screen = view.findViewById(R.id.lt_screen);
        commonAdapter = new CommonAdapter<String>(context, R.layout.item_screen, list) {
            @Override
            public void convert(ViewHolder viewHolder, String t) {
                setSelectedPosition(select);
                TextView tv_name = viewHolder.getView(R.id.tv_name);
                int position = viewHolder.getPosition();
                if (position == getSelectedPosition()) {
                    viewHolder.getView(R.id.img_ok).setVisibility(View.VISIBLE);
                    tv_name.setTextColor(Color.parseColor("#1296db"));
                } else {
                    tv_name.setTextColor(Color.parseColor("#000000"));
                    viewHolder.getView(R.id.img_ok).setVisibility(View.GONE);
                }
                viewHolder.setText(R.id.tv_name, t);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelectedPosition(position);
                        commonAdapter.notifyDataSetChanged();
                        clickListener.click(position);
                    }
                });
            }
        };
        lt_screen.setAdapter(commonAdapter);
    }


    public interface ClickListener {
        void click(int position);
    }
}
