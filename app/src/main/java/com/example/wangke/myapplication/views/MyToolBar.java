package com.example.wangke.myapplication.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.DeviceUtils;
import com.example.wangke.myapplication.utils.Tool;

public class MyToolBar extends Toolbar {
    private TextView textTile;
    private ImageView imgBack;
    private ImageView imgRight;
    private MyToolBar.LayoutParams paramsTitle;
    private MyToolBar.LayoutParams paramsBack;
    private MyToolBar.LayoutParams paramsImgRight;
    private ToolBarLeftOnClickListener toolBarLeftOnClickListener;
    private ToolBarRightOnClickListener toolBarRightOnClickListener;


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
        drawImgRight();
        addView(imgBack, paramsBack);
        addView(textTile, paramsTitle);
        addView(imgRight, paramsImgRight);
    }

    public void setTitle(String title) {
        textTile.setText(title);
    }

    public void setRightImg(int res) {
        imgRight.setBackgroundResource(res);
    }

    public void drawTitle() {
        paramsTitle = new MyToolBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTitle.gravity = Gravity.CENTER;
        textTile = new TextView(getContext());
        textTile.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textTile.setTextSize(16);
    }

    public void drawBack() {
        paramsBack = new MyToolBar.LayoutParams(DeviceUtils.dip2px(getContext(), 20), DeviceUtils.dip2px(getContext(), 20));
        paramsBack.gravity = Gravity.CENTER_VERTICAL;
        imgBack = new ImageView(getContext());
        imgBack.setBackgroundResource(R.mipmap.back);
        imgBack.setOnClickListener(v -> {
            if (!Tool.isEmpty(toolBarLeftOnClickListener)){
                toolBarLeftOnClickListener.setToolBarLeftOnClick();
            }
        });

    }

    public void drawImgRight() {
        paramsImgRight = new MyToolBar.LayoutParams(DeviceUtils.dip2px(getContext(), 20), DeviceUtils.dip2px(getContext(), 20));
        paramsImgRight.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        paramsImgRight.setMargins(0, 0, DeviceUtils.dip2px(getContext(), 20), 0);
        imgRight = new ImageView(getContext());
        imgRight.setBackgroundResource(R.mipmap.back);
        imgRight.setOnClickListener(v -> {
            if (!Tool.isEmpty(toolBarLeftOnClickListener)){
                toolBarRightOnClickListener.setToolBarRightOnClick();
            }
        });
    }

    public interface ToolBarLeftOnClickListener {
        void setToolBarLeftOnClick();
    }

    public interface ToolBarRightOnClickListener {
        void setToolBarRightOnClick();
    }

    public void setToolBarLeftOnClickListener(ToolBarLeftOnClickListener toolBarLeftOnClickListener) {
        this.toolBarLeftOnClickListener = toolBarLeftOnClickListener;
    }

    public void setToolBarRightOnClickListener(ToolBarRightOnClickListener toolBarRightOnClickListener) {
        this.toolBarRightOnClickListener = toolBarRightOnClickListener;
    }
}
