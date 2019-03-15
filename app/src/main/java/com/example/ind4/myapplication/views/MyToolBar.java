package com.example.ind4.myapplication.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ind4.myapplication.R;
import com.example.ind4.myapplication.utils.DeviceUtils;

public class MyToolBar extends Toolbar {
    private TextView textTile;
    private ImageView imgBack;
    private MyToolBar.LayoutParams paramsTitle;
    private MyToolBar.LayoutParams paramsBack;
    private ImgBackOnclickListener imgBackOnclickListener;


    public MyToolBar(Context context) {
        super(context);
        initDraw();
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDraw();
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    public void initDraw() {
        drawBack();
        drawTitle();
        addView(imgBack,paramsBack);
        addView(textTile, paramsTitle);
    }

    public void setTitle(String title) {
        textTile.setText(title);
    }

    public void drawTitle() {
        paramsTitle = new MyToolBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTitle.gravity = Gravity.CENTER;
        textTile = new TextView(getContext());
        textTile.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textTile.setTextSize(16);
    }
    public void drawBack(){
        paramsBack = new MyToolBar.LayoutParams(DeviceUtils.dip2px(getContext(),20), DeviceUtils.dip2px(getContext(),20));
        paramsBack.gravity=Gravity.CENTER_VERTICAL;
        imgBack = new ImageView(getContext());
        imgBack.setBackgroundResource(R.mipmap.back);
        imgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBackOnclickListener.setImgBackOnclick();
            }
        });

    }
    public interface ImgBackOnclickListener{
        void setImgBackOnclick();
    }
    public void setImgBackOnclick(ImgBackOnclickListener imgBackOnclickListener){
        this.imgBackOnclickListener=imgBackOnclickListener;
    }
}
