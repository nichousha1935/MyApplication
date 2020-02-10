package com.example.wangke.myapplication.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.adapter.CommonAdapter;
import com.example.wangke.myapplication.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class GaoDeSearchActivity extends BaseActivity implements Inputtips.InputtipsListener {
    private List<Tip> list_search_data = new ArrayList<>();
    private ListView lt_search;
    private EditText et_search;
    private CommonAdapter<Tip> commonAdapter;
    private ImageView img_back;
    private LatLng latLng;
    private String cityName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaode_search);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        lp.width = width; // 宽度
        lp.height = (int) (height * 0.9); // 高度
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        latLng = getIntent().getParcelableExtra("LatLng");
        cityName=getIntent().getStringExtra("cityName");
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this::widgetClick);
        lt_search = findViewById(R.id.lt_search);
        et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                InputtipsQuery input_query = new InputtipsQuery(s.toString(), cityName);
                input_query.setCityLimit(true);//限制在当前城市
                Inputtips inputTips = new Inputtips(GaoDeSearchActivity.this, input_query);
                inputTips.setInputtipsListener(GaoDeSearchActivity.this::onGetInputtips);
                inputTips.requestInputtipsAsyn();//发送请求
            }
        });

        commonAdapter = new CommonAdapter<Tip>(this, R.layout.item_search, list_search_data) {
            @Override
            public void convert(ViewHolder viewHolder, Tip t) {
                if (t.getPoint() != null) {
                    LatLng latLngEnd = new LatLng(t.getPoint().getLatitude(), t.getPoint().getLongitude());

                    float distance = AMapUtils.calculateLineDistance(latLng, latLngEnd);//距离计算
                    int distance1 = Math.round(distance);
                    if (distance1 < 1000) {
                        viewHolder.setText(R.id.tv_distance, Integer.toString(distance1) + "m");
                    } else {
                        viewHolder.setText(R.id.tv_distance, distance1 / 1000 + "km");
                    }

                    viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setResult(RESULT_OK, new Intent().putExtra("location", latLngEnd));
                            finish();
                        }
                    });
                }
                viewHolder.setText(R.id.tv_name, t.getName());
                viewHolder.setText(R.id.tv_address, t.getDistrict() + t.getAddress());
            }
        };
        lt_search.setAdapter(commonAdapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initBind() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == 1000) {
            list_search_data.clear();
            list_search_data.addAll(list);
            commonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_down_exit);
    }
}
