package com.example.wangke.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.views.MyLinearLayout;

/**
 * 文件描述:
 *
 * @author Administrator
 * @date 2019/1/5
 */
public class NewFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_child_content,null);
        ImageView imageView=view.findViewById(R.id.img_click);
        final MyLinearLayout myLinearLayout=view.findViewById(R.id.mylin_click);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"事件分发",Toast.LENGTH_SHORT).show();
            }
        });
        myLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(getContext(),"456",Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        myLinearLayout.performClick();
                        break;
                }
                return true;
            }
        });
        return view;
    }
}
