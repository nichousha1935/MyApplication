package com.example.wangke.myapplication.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.wangke.myapplication.R;

public class CodeActivity extends BaseActivity {
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

    }

    public void find1(){//activity包裹fragment
        //步骤一：添加一个FragmentTransaction的实例

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //步骤二：用add()方法加上Fragment的对象rightFragment

        Fragment fragment = new Fragment();

//        transaction.add(R.id.right,fragment);

        transaction.commit();
    }

    public void find2(){//底部菜单栏的显示与隐藏
        //取消发送，显示底部菜单栏
//        mHandler.removeMessages(COLLAPSE_STATUS_BAR);
//        showBottomUIMenu();

//        //延时毫秒数
//        private static final long COLLAPSE_SB_PERIOD = 100;
//        //id
//        private static final int COLLAPSE_STATUS_BAR = 1000;
//        @SuppressLint("HandlerLeak")
//        private Handler mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case COLLAPSE_STATUS_BAR:
//                        collapse(MainActivity.this, true);
//                        sendEmptyMessageDelayed(COLLAPSE_STATUS_BAR, COLLAPSE_SB_PERIOD);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//        };
//
//        public void secondHide() {
//            int flags = getWindow().getDecorView().getSystemUiVisibility();
//            getWindow().getDecorView().setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
//
//            mHandler.sendEmptyMessageDelayed(COLLAPSE_STATUS_BAR, COLLAPSE_SB_PERIOD);
//
//        }
//        public static void collapse(Activity activity, boolean enable) {
//            Window window = activity.getWindow();
//            if (enable) {
//                WindowManager.LayoutParams attr = window.getAttributes();
//                window.setAttributes(attr);
//                window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//                int flags = window.getDecorView().getSystemUiVisibility();
//                window.getDecorView().setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
//
//                attr.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            } else {
//                WindowManager.LayoutParams attr = window.getAttributes();
//                attr.flags &= (WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                window.setAttributes(attr);
//                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            }
//        }
//
//        protected void showBottomUIMenu() {
//            int flags;
//            int curApiVersion = android.os.Build.VERSION.SDK_INT;
//            // This work only for android 4.4+
//            if (curApiVersion >= Build.VERSION_CODES.KITKAT) {
//                // This work only for android 4.4+
//                // hide navigation bar permanently in android activity
//                // touch the screen, the navigation bar will not show
//                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//
//            } else {
//                // touch the screen, the navigation bar will show
//                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//            }
//
//            // must be executed in main thread :)
//            getWindow().getDecorView().setSystemUiVisibility(flags);
//        }
    }
}
