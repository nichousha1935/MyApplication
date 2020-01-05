package com.example.wangke.myapplication.views;

import android.content.Context;
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

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.adapter.CommonAdapter;
import com.example.wangke.myapplication.adapter.ViewHolder;
import com.example.wangke.myapplication.utils.DeviceUtils;

import java.util.List;

public class ScreenPopupWindows extends PopupWindow  {
    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    private ListView lt_search;
    private EditText et_search;
    private CommonAdapter<String> commonAdapter;
    private ClickListener clickListener;

    public ScreenPopupWindows(Context context, List<String> list, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.list = list;
        init();
    }

    public void init() {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_gaode_search, null);
        setContentView(view);
        setWidth(DeviceUtils.getScreenWidth(context));
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        et_search = view.findViewById(R.id.lt_search);
        lt_search = view.findViewById(R.id.lt_search);
        lt_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickListener.click(position);
            }
        });
        commonAdapter = new CommonAdapter<String>(context, R.layout.item_screen, list) {
            @Override
            public void convert(ViewHolder viewHolder, String t) {
//                viewHolder.setText(R.id.tv_name, t.getName());
//                viewHolder.setText(R.id.tv_address, t.getDistrict() + t.getAddress());
            }
        };
        lt_search.setAdapter(commonAdapter);
    }


    public interface ClickListener {
        void click(int position);
    }
}
