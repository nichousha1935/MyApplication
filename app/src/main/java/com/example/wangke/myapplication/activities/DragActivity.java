package com.example.wangke.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wangke.myapplication.R;

public class DragActivity extends BaseActivity implements View.OnTouchListener {
    private RelativeLayout mRlFather;
    private ImageView bowlImageView,glutinousriceballsImageView;
    private DisplayMetrics dm;
    private int lastX, lastY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_drag);
        dm = getResources().getDisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels - 50;
    }

    @Override
    public void initView() {
        mRlFather =  findViewById(R.id.rl_father);
        bowlImageView = findViewById(R.id.img_bowl);
        glutinousriceballsImageView=findViewById(R.id.img_glutinousriceballs);
    }

    @Override
    public void initBind() {
        mRlFather.setOnTouchListener(this);
        bowlImageView.setOnTouchListener(this);
        glutinousriceballsImageView.setOnTouchListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int ea = event.getAction();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels;
        switch(v.getId()){
            case R.id.rl_father:
                break;
            case R.id.img_bowl:
            case R.id.img_glutinousriceballs:
                switch(ea){
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int l = v.getLeft() + dx;
                        int b = v.getBottom() + dy;
                        int r = v.getRight() + dx;
                        int t = v.getTop() + dy;
                        // 下面判断移动是否超出屏幕
                        if (l < 0) {
                            l = 0;
                            r = l + v.getWidth();
                        }
                        if (t < 0) {
                            t = 0;
                            b = t + v.getHeight();
                        }
                        if (r > screenWidth) {
                            r = screenWidth;
                            l = r - v.getWidth();
                        }
                        if (b > screenHeight) {
                            b = screenHeight;
                            t = b - v.getHeight();
                        }
                        v.layout(l, t, r, b);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        v.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
            default :
                break;
        }
        return true;
    }
}
