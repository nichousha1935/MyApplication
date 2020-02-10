package com.example.wangke.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.wangke.myapplication.R;

public class AddInfoSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinfoselect);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        lp.width = (int) (width * 0.5); // 宽度
        lp.height = (int) (height * 0.7); // 高度
        lp.gravity = Gravity.RIGHT;
        dialogWindow.setAttributes(lp);
        initView();
        initBind();
        initData();
    }

    public void initView() {
        img_back = findViewById(R.id.img_back);
    }

    public void initBind() {
        img_back.setOnClickListener(this::onClick);
    }

    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_right_exit);
    }
}
