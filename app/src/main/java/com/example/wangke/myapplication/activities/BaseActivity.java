package com.example.wangke.myapplication.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.StatusBarHelper;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.views.MyToolBar;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;
    /**
     * 是否允许旋转屏幕
     **/
    private boolean isAllowScreenRotate = false;
    private LinearLayout linearLayout;
    private MyToolBar myToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSetStatusBar) {
            steepStatusBar();
        }
        if (isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_base);
        myToolBar=findViewById(R.id.toolbar_base);
        linearLayout=findViewById(R.id.lin_content);
    }


    public void setActivityContentView(@LayoutRes int layoutResID) {
        linearLayout.addView(LayoutInflater.from(this).inflate(layoutResID,null));
        initView();
        initBind();
        initData();
    }

    public void setActivityContentView(View view) {
        linearLayout.addView(view);
        initView();
        initBind();
        initData();
    }

    public abstract void initView();

    public abstract void initData();

    public abstract void initBind();

    public MyToolBar getToolBar(){
        return myToolBar;
    }

    public void toast(Context context, String text) {
        ToastUtil.showToast(context, text);
    }

    public void toast(Context context, int resId) {
        ToastUtil.showToast(context, resId);
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 显示软键盘
     */
    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void steepStatusBar() {
        // 设置状态栏黑色字体
        StatusBarHelper.translucent(this);
        StatusBarHelper.setStatusBarLightMode(this);
        //大于5.0，全面屏虚拟按钮背景色设置白色
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.WHITE);
        }
    }

    /**
     * 是否设置沉浸状态栏
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    public void setScreenRoate(boolean isAllowScreenRotate) {
        this.isAllowScreenRotate = isAllowScreenRotate;
    }

    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }
}
