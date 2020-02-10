package com.example.wangke.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.views.ScreenPopupWindows;

import java.util.ArrayList;
import java.util.List;

public class DataListFragment extends Fragment implements View.OnClickListener {
    private LinearLayout lin_one, lin_two, lin_three, lin_four;
    private TextView tv_one, tv_two, tv_three, tv_four;
    private ImageView img_one, img_two, img_three, img_four;
    private ScreenPopupWindows screenPopupWindows;
    private List<String> list=new ArrayList<>();
    private int selectDiatance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datalist, null);
        lin_one = view.findViewById(R.id.lin_one);
        lin_two = view.findViewById(R.id.lin_two);
        lin_three = view.findViewById(R.id.lin_three);
        lin_four = view.findViewById(R.id.lin_four);
        tv_one = view.findViewById(R.id.tv_one);
        tv_two = view.findViewById(R.id.tv_two);
        tv_three = view.findViewById(R.id.tv_three);
        tv_four = view.findViewById(R.id.tv_four);
        img_one = view.findViewById(R.id.img_one);
        img_two = view.findViewById(R.id.img_two);
        img_three = view.findViewById(R.id.img_three);
        img_four = view.findViewById(R.id.img_four);

        lin_one.setOnClickListener(this::onClick);
        lin_two.setOnClickListener(this::onClick);
        lin_three.setOnClickListener(this::onClick);
        lin_four.setOnClickListener(this::onClick);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.add("1km");
        list.add("3km");
        list.add("5km");
        list.add("10km");
        list.add("全部");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_one:
                tv_one.setTextColor(Color.parseColor("#1296db"));
                img_one.setBackgroundResource(R.mipmap.little_arrow_up);
                screenPopupWindows=new ScreenPopupWindows(getContext(), list,selectDiatance, new ScreenPopupWindows.ClickListener() {
                    @Override
                    public void click(int position) {
                        selectDiatance=position;
                        ToastUtil.showToast(getContext(),list.get(position));
                        screenPopupWindows.dismiss();
                        tv_one.setText(list.get(position));
                    }
                });
                screenPopupWindows.showAsDropDown(lin_four);
                //控制popupwindows消失需要执行的逻辑
                screenPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tv_one.setTextColor(Color.parseColor("#000000"));
                        img_one.setBackgroundResource(R.mipmap.little_arrow_down);
                    }
                });
                break;
            case R.id.lin_two:
                break;
            case R.id.lin_three:
                break;
            case R.id.lin_four:
                break;
            default:
                break;
        }
    }
}
