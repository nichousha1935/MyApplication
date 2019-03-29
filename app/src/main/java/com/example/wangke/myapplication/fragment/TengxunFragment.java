package com.example.wangke.myapplication.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.utils.Utils;
import com.example.wangke.myapplication.R;

import com.example.wangke.myapplication.activities.WeakActivity;
import com.example.wangke.myapplication.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

public class TengxunFragment extends Fragment implements View.OnClickListener {
    //扇形菜单按钮
    private int res[] = {R.id.circle_menu_button_1, R.id.circle_menu_button_2, R.id.circle_menu_button_3, R.id.circle_menu_button_4, R.id.circle_menu_button_5};
    private List<ImageView> imageViews = new ArrayList<>();
    //菜单是否展开的flag,false表示没展开
    private boolean mFlag = false;
    private TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        for (int i = 0; i < res.length; i++) {
            ImageView imageView = (ImageView) view.findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViews.add(imageView);
        }
        textView = view.findViewById(R.id.tv_content);
        textView.setText("腾讯");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(getContext(),textView.getText().toString(),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), WeakActivity.class));
                            }
                        });

                    }
                }).start();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_menu_button_1:
                if (mFlag == false) {
                    showEnterAnim(100); //100为扇形半径dp值
                } else {
                    showExitAnim(100);
                }
                break;
        }
    }

    //显示扇形菜单的属性动画
    private void showEnterAnim(int dp) {
        //for循环来开始小图标的出现动画
        for (int i = 1; i < res.length; i++) {
            AnimatorSet set = new AnimatorSet();
            double x = -Math.cos(0.5 / (res.length - 2) * (i - 1) * Math.PI) * DeviceUtils.dip2px(getContext(), dp);
            double y = -Math.sin(0.5 / (res.length - 2) * (i - 1) * Math.PI) * DeviceUtils.dip2px(getContext(), dp);
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i), "translationX", (float) (x * 0.25), (float) x),
                    ObjectAnimator.ofFloat(imageViews.get(i), "translationY", (float) (y * 0.25), (float) y),
                    ObjectAnimator.ofFloat(imageViews.get(i), "alpha", 0, 1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100 * i);
            set.start();
        }
         //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imageViews.get(0), "rotation", 0, 45).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

        //菜单状态置打开
        mFlag = true;
    }

    //退出扇形菜单的属性动画
    private void showExitAnim(int dp) {
        //for循环来开始小图标的出现动画
        for (int i = 1; i < res.length; i++) {
            AnimatorSet set = new AnimatorSet();
            double x = Math.cos(0.5 / (res.length - 2) * (i - 1) * Math.PI) * DeviceUtils.dip2px(getContext(), dp);
            double y = Math.sin(0.5 / (res.length - 2) * (i - 1) * Math.PI) * DeviceUtils.dip2px(getContext(), dp);
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationX",(float)(x*0.25),(float)x),
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float)(y*0.25),(float)y)
                    ,ObjectAnimator.ofFloat(imageViews.get(i),"alpha",1,0).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100*i);
            set.start();
        }
        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imageViews.get(0),"rotation",45,0).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

        //菜单状态置打开
        mFlag = false;
    }
}
