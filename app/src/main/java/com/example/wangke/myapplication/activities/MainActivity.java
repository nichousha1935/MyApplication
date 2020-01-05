package com.example.wangke.myapplication.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.fragment.AliFragment;
import com.example.wangke.myapplication.fragment.BaiduFragment;
import com.example.wangke.myapplication.fragment.TengxunFragment;
import com.example.wangke.myapplication.fragment.WangyiFragment;
import com.example.wangke.myapplication.fragment.WeiruanFragment;
import com.example.wangke.myapplication.utils.ToastUtil;
import com.example.wangke.myapplication.views.NoScrollViewPager;


public class MainActivity extends BaseActivity {
    private TabLayout tab_net;
    private NoScrollViewPager viewpage_net;
    private String[] title = new String[]{"腾讯", "微软", "网易", "百度", "阿里"};
    private long time;
    private static final int requestPermission = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setSteepStatusBar(true);
        setScreenRoate(false);
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_main);
        getToolBar().setTitle("鼎集智能");
        //getToolBar().setRightImg(R.mipmap.ic_launcher);
        getToolBar().setToolBarLeftOnClickListener(() -> {
            toast(MainActivity.this, "怎么可能让你离开呢");
        });
        getToolBar().setToolBarRightOnClickListener(() -> toast(MainActivity.this, "我是右边的按钮"));
        // init();
    }

    @Override
    public void initView() {
        tab_net = findViewById(R.id.tab_order);
        viewpage_net = findViewById(R.id.viewpage_order);
    }

    @Override
    public void initData() {
        viewpage_net.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewpage_net.setCurrentItem(0);
        tab_net.setupWithViewPager(viewpage_net);
        for (int i = 0; i < tab_net.getTabCount(); i++) {
            tab_net.getTabAt(i).setCustomView(new PagerAdapter(getSupportFragmentManager()).getTabView(i));
        }
    }

    @Override
    public void initBind() {
        checkPermission();
    }

    @Override
    public void widgetClick(View v) {

    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override

        public Fragment getItem(int position) {
            if (position == 0) {
                return new TengxunFragment();
            } else if (position == 1) {
                return new WeiruanFragment();
            } else if (position == 2) {
                return new WangyiFragment();
            } else if (position == 3) {
                return new BaiduFragment();
            } else if (position == 4) {
                return new AliFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return title.length;

        }

        @Override
        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "腾讯";
//                case 1:
//                    return "微软";
//                case 2:
//                    return "网易";
//                case 3:
//                    return "百度";
//                case 4:
//                    return "阿里";
//                default:
//                    return "";
//
//
//            }
            return null;
        }

        public View getTabView(int position) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_tab, null);
            TextView textView = view.findViewById(R.id.tv_title);
            textView.setText(title[position]);
//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            },1000);
            return view;

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    public void init() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView textView = new TextView(MainActivity.this);
        textView.setText("悬浮窗");
        textView.setBackgroundColor(Color.parseColor("#FF3499"));
        windowManager.addView(textView, params);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestPermission:
                if (permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED ) {

                    }
                }
                break;
            case TengxunFragment.RC_TAKE_PHOTO:
//                if (permissions[0].equals(Manifest.permission.READ_CALENDAR)) {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    FixDexUtils.loadFixedDex(this, Environment.getExternalStorageDirectory());
//                    startActivity(new Intent(this, HotFixActivity.class));
//                }
//            }
                break;
            case TengxunFragment.RC_CHOOSE_PHOTO:
                Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
                intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentToPickPic, 2);
                break;
            case TengxunFragment.RC_UPDATE_APP:
                startActivity(new Intent(this, VersionUpdateActivity.class));
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                toast(this, "再按一次退出程序");
                time = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return false;
    }

    public void checkPermission() {
        int permissionLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        int permissionCalendar = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true,它在用户选择"不再询问"的情况下返回false
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ToastUtil.showToast(this, "请赋予App读写权限");
            } else {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, requestPermission);
            }
        } else {
            //startActivity(new Intent(this, VersionUpdateActivity.class));
        }
    }
}
